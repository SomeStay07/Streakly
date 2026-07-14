import SwiftUI
import SharedLogic

@main
struct StreaklyApp: App {
    private let storage = KeyValueStorage_iosKt.createKeyValueStorage()

    var body: some Scene {
        WindowGroup { RootView(storage: storage) }
    }
}

private struct RootView: View {
    let storage: KeyValueStorage
    @State private var onboardingDone: Bool

    init(storage: KeyValueStorage) {
        self.storage = storage
        _onboardingDone = State(
            initialValue: storage.getBool(key: KeyValueStorageKt.KEY_ONBOARDING_COMPLETED)
        )
    }

    var body: some View {
        if onboardingDone {
            HomeView()
        } else {
            OnboardingView {
                storage.putBool(key: KeyValueStorageKt.KEY_ONBOARDING_COMPLETED, value: true)
                onboardingDone = true
            }
        }
    }
}

