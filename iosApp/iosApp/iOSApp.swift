import SwiftUI
import SharedLogic

final class OnboardingViewModelAdapter: ObservableObject {
    @Published private(set) var state: OnboardingState

    private let viewModel: OnboardingViewModel
    private var handle: WatchHandle?

    init() {
        let repository = OnboardingRepository(
            storage: KeyValueStorage_iosKt.createKeyValueStorage()
        )
        viewModel = OnboardingViewModel(repository: repository)
        state = viewModel.currentState()
        handle = viewModel.watchState { [weak self] newState in
            self?.state = newState
        }
    }

    deinit {
        handle?.close()
    }

    func send(_ intent: OnboardingIntent) {
        viewModel.onIntent(intent: intent)
    }
}

@main
struct StreaklyApp: App {
    @StateObject private var onboarding = OnboardingViewModelAdapter()

    var body: some Scene {
        WindowGroup {
            if onboarding.state.completed {
                NavigationStack { HomeView() }
            } else {
                OnboardingView {
                    onboarding.send(OnboardingIntentStartTapped.shared)
                }
            }
        }
    }
}
