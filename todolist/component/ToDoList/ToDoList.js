import ToDoItem from "./ToDoItem";
import './ToDoList.css';

const ToDoList = (props) => {
	if(props.items.length === 0) {
		return <h2 className="todos-list__fallback">TODOが検索できません。</h2>
	}
	
	const sendDeleteSignal = (itemKey) => {
		props.onDelete(itemKey);
	}

	const filteredItems = (props.items).map((todo) => {
		return (
			<ToDoItem 
				key={todo.id}
				title={todo.title}
				amount={todo.amount}
				date={todo.date}
				itemKey={todo.id}
				onDelete={sendDeleteSignal} />
		)
	});
	
	return (
		<ul className="todos-list">
			{filteredItems}
		</ul>
	);
}

export default ToDoList;