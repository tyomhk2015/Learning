import { useState } from 'react';
import './NewToDo.css';
import NewToDoForm from './NewToDoForm';

const NewToDo = (props) => {
  const [isEditing, setIsEditinState] = useState(false);

  const addToDoDataHandler = (enteredToDoData) => {
    const ToDoData = {
      ...enteredToDoData,
      id: 'e' + props.itemLength + 1
    }
    // Data transferred to parent component
    props.onAddToDo(ToDoData);
  };

  const startEditingHandler = () => {
    setIsEditinState(true);
  }

  const stopEditingHandler = () => {
    setIsEditinState(false);
  }

  return (
    <div className="new-todo">
      {!isEditing && <button onClick={startEditingHandler}>作成</button>}
      {isEditing && <NewToDoForm onAddToDoData={addToDoDataHandler} onCancelEditing={stopEditingHandler} />}
    </div>
  );
}

export default NewToDo;