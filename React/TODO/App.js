import { useState } from "react";
import ToDos from "./component/ToDoList/ToDos";
import NewToDo from "./component/NewToDo/NewToDo";

const DUMMY_EXPENSES = [
	{
		id: 'e1',
		title: '掃除',
		amount: 1,
		date: new Date(2020, 7, 14),
	},
	{ id: 'e2', 
		title: '映画を見る', 
		amount: 2, 
		date: new Date(2021, 2, 12) },
	{
		id: 'e3',
		title: '晩御飯つくり',
		amount: 1,
		date: new Date(2021, 2, 28),
	},
	{
		id: 'e4',
		title: '買い物',
		amount: 1.5,
		date: new Date(2019, 4, 12),
	},
	{
		id: 'e5',
		title: '運動',
		amount: 2,
		date: new Date(2019, 2, 12),
	},
	{
		id: 'e6',
		title: 'パチンコ',
		amount: 3,
		date: new Date(2021, 1, 12),
	},
	{
		id: 'e7',
		title: 'ディズニーランド',
		amount: 6,
		date: new Date(2021, 9, 12),
	},
];

const App = () => {
	const [todos, setToDos] = useState(DUMMY_EXPENSES);

	const addToDoHandler = (todo) => {
		setToDos((prevToDos) => {
			return [todo, ...prevToDos];
		});
	}
	
	const deleteTodo = (itemKey) => {
		const deletedTodos = todos.filter(todo => !todo.id.includes(itemKey));
		console.log(deletedTodos);
		setToDos(deletedTodos);
	}

	console.log(todos);
	return (
		<div className="App">
			<NewToDo itemLength={todos.length} onAddToDo={addToDoHandler}/>
			<ToDos items={todos} onDelete={deleteTodo} />
		</div>
	);
}

export default App;