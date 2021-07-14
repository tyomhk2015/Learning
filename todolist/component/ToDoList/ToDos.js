import { useState } from 'react';
import ToDoFilter from "./ToDoFilter";
import Card from './Card';
import ToDoList from './ToDoList';
import ToDoChart from './ToDoChart';
import './ToDos.css';

const ToDos = (props) => {
	const [chosenYear, setChosenYear] = useState('2020');

	const pickedYearHandler = (pickedYear) => {
		setChosenYear(pickedYear);
	}

	const items = props.items.filter((todo) => todo.date.getFullYear() === parseInt(chosenYear));

	const sendDeleteSignal = (itemKey) => {
		props.onDelete(itemKey);
	}

	return (
		<Card className="todos">
			<ToDoFilter selectedYear={chosenYear} onPickedYear={pickedYearHandler}/>
			<ToDoList items={items} onDelete={sendDeleteSignal} />
			<ToDoChart items={items}/>
		</Card>
	);
}

export default ToDos;