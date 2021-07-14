import './ToDoDate.css';

const ToDoDate = (props) => {
	const year = props.date.toLocaleString('ja-JP', {year: 'numeric'});
	const month = props.date.toLocaleString('ja-JP', {month: 'numeric'});
	const day = props.date.toLocaleString('ja-JP', {day: 'numeric'});

	return (
		<div className="todo-date">
			<div className="todo-date__year">{year}</div>
			<div className="todo-date__month">{month}</div>
			<div className="todo-date__day">{day}</div>
		</div>
	);
}

export default ToDoDate;