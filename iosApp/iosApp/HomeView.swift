import SwiftUI

struct HomeView: View {
    var body: some View {
        NavigationStack {
            VStack {
                Spacer()
                Text("Пока пусто").foregroundStyle(.secondary)
                Spacer()
                Button {
                } label: {
                    Label("Habit", systemImage: "plus").frame(maxWidth: .infinity)
                }
                .buttonStyle(.borderedProminent).padding()
            }
            .navigationTitle("Streakly")
        }
    }
}
