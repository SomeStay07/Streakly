import SwiftUI
import SharedLogic

final class HomeViewModelAdapter: ObservableObject {
    @Published private(set) var state: HomeState

    private let viewModel: HomeViewModel
    private var handle: WatchHandle?

    init() {
        viewModel = HomeViewModel(repository: HabitsRepository())
        state = viewModel.currentState()

        handle = viewModel.watchState { [weak self] newState in
            self?.state = newState
        }
    }

    deinit {
        handle?.close()
    }

    func send(_ intent: HomeIntent) {
        viewModel.onIntent(intent: intent)
    }
}

struct HomeView: View {
    @StateObject private var adapter = HomeViewModelAdapter()
    @State private var draft = ""

    var body: some View {
        VStack {
            HStack {
                TextField("Новая привычка", text: $draft)
                    .textFieldStyle(.roundedBorder)
                Button("+") {
                    adapter.send(HomeIntentAddTapped(name: draft))
                    draft = ""
                }
            }
            .padding(.horizontal)

            List(adapter.state.habits, id: \.id) { habit in
                HStack {
                    Image(systemName: habit.doneToday ? "checkmark.circle.fill" : "circle")
                        .foregroundStyle(habit.doneToday ? .green : .secondary)
                    Text(habit.name)
                }
                .contentShape(Rectangle())
                .onTapGesture {
                    adapter.send(HomeIntentHabitTapped(id: habit.id))
                }
            }
            .listStyle(.plain)
        }
        .navigationTitle("Streakly")
    }
}
