import SwiftUI
import SharedLogic

@MainActor
final class OnboardingViewModelAdapter: ObservableObject {
    @Published private(set) var state: OnboardingState

    private let viewModel: OnboardingViewModel
    private var stateTask: Task<Void, Never>?

    init() {
        let repository = OnboardingRepository(storage: createKeyValueStorage())
        viewModel = OnboardingViewModel(repository: repository)
        state = viewModel.state.value
        stateTask = Task { [weak self, viewModel] in
            for await newState in viewModel.state {
                self?.state = newState
            }
            viewModel.destroy()
        }
    }

    deinit {
        stateTask?.cancel()
    }

    func send(_ intent: OnboardingIntent) {
        viewModel.onIntent(intent: intent)
    }
}

@main
struct StreaklyApp: App {
    @StateObject private var onboarding = OnboardingViewModelAdapter()
    @State private var databaseReady = false
    @State private var showDatabaseError = false
    @State private var databaseError = ""

    var body: some Scene {
        WindowGroup {
            Group {
                if !databaseReady {
                    ProgressView()
                } else if onboarding.state.completed {
                    NavigationStack { HomeView() }
                } else {
                    OnboardingView {
                        onboarding.send(OnboardingIntentStartTapped.shared)
                    }
                }
            }
            .task {
                do {
                    try await openDatabase()
                    databaseReady = true
                } catch {
                    databaseError = error.localizedDescription
                    showDatabaseError = true
                }
            }
            .alert("База данных недоступна", isPresented: $showDatabaseError) {
                Button("Ок", role: .cancel) {}
            } message: {
                Text(databaseError)
            }
        }
    }
}
