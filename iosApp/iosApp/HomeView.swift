import SwiftUI
import SharedLogic

@MainActor
final class HomeViewModelAdapter: ObservableObject {
    @Published private(set) var state: HomeState
    @Published var showEmptyName = false

    private let viewModel: HomeViewModel
    private var stateTask: Task<Void, Never>?
    private var effectsTask: Task<Void, Never>?

    init() {
        viewModel = createHomeViewModel()
        state = viewModel.state.value

        stateTask = Task { [weak self, viewModel] in
            for await newState in viewModel.state {
                self?.state = newState
            }
            viewModel.destroy()   // цикл кончился отменой из deinit, следом гасим скоуп ViewModel
        }
        effectsTask = Task { [weak self, viewModel] in
            for await effect in viewModel.effects {
                switch onEnum(of: effect) {
                case .emptyName: self?.showEmptyName = true
                }
            }
        }
    }

    deinit {
        stateTask?.cancel()
        effectsTask?.cancel()
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
        .alert("Введи название привычки", isPresented: $adapter.showEmptyName) {
            Button("Ок", role: .cancel) {}
        }
    }
}
