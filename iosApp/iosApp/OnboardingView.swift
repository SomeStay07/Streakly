import SwiftUI

struct OnboardingView: View {
    let onFinish: () -> Void

    var body: some View {
        TabView {
            OnboardingPage(title: "Streakly", text: "Трекер привычек, который держит твой streak")
            OnboardingPage(title: "Серии и heatmap", text: "Отмечай привычки, расти streak, смотри прогресс")
            VStack(spacing: 24) {
                OnboardingPage(title: "Поехали", text: "Заводим первую привычку")
                Button("Начать", action: onFinish).buttonStyle(.borderedProminent)
            }
        }
        .tabViewStyle(.page)
        .indexViewStyle(.page(backgroundDisplayMode: .always))
    }
}

