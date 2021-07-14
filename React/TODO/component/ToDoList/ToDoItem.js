import './ToDoItem.css';
import Card from './Card';
import ToDoDate from './ToDoDate';

const ToDoItem = (props) => {

	const deleteTodo = (event) => {
		props.onDelete(event.target.value);
	};

	return (
		<li>
			<Card className="todo-item">
				<ToDoDate date={props.date}/>
				<div className="todo-item__description">
					<h2>{props.title}</h2>
					<div className="todo-item__price">{props.amount}時間</div>
					<button onClick={deleteTodo} value={props.itemKey}>削除</button>
				</div>
			</Card>
		</li>
	);
}

export default ToDoItem;
