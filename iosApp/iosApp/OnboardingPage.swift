import SwiftUI

struct OnboardingPage: View {
    let title: String
    let text: String

    var body: some View {
        VStack(spacing: 16) {
            Image(systemName: "circle.grid.2x2")
                .resizable().frame(width: 80, height: 80).foregroundStyle(.tint)
            Text(title).font(.title).bold()
            Text(text).font(.body).foregroundStyle(.secondary).multilineTextAlignment(.center)
        }
        .padding().frame(maxWidth: .infinity, maxHeight: .infinity)
    }
}
