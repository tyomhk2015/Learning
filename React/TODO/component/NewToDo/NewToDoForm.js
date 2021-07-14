import { useState } from 'react';
import './NewToDoForm.css';

const NewToDoForm = (props) => {
  // Single State
  const [enteredTitle, setEnteredTitle] = useState('');
  const [enteredAmount, setEnteredAmount] = useState('');
  const [enteredDate, setEnteredDate] = useState('');

  // Multiple States
  // const [userInput, setUserInput] = useState({
  //   enteredTitle: '',
  //   enteredAmount: '',
  //   enteredDate: ''
  // });

  const titleChangeHandler = (event) => {
    setEnteredTitle(event.target.value);

    // setUserInput({
    //   ...userInput,
    //   enteredTitle: event.target.value,
    // });

    // Keep the latest state.
    // setUserInput((prevState) => {
    //   return {...prevState, enteredTitle: event.target.value};
    // });
  };

  const amountChangeHandler = (event) => {
    setEnteredAmount(event.target.value);
    
    // setUserInput({
    //   ...userInput,
    //   enteredAmount: event.target.value,
    // });
  };

  const dateChangeHandler = (event) => {
    setEnteredDate(event.target.value);

    // setUserInput({
    //   ...userInput,
    //   enteredDate: event.target.value,
    // });
  };

  const submitHandler = (event) => {
    // Disable the page reload request.
    event.preventDefault();

    const todoData = {
      title: enteredTitle,
      amount: +enteredAmount,
      // Convert the string date into a 'Date' object.
      date: new Date(enteredDate),
    };

    // Data transferred to parent component.
    props.onAddToDoData(todoData);
    resetForm();
  };

  const cancelEditingForm = () => {
    props.onCancelEditing();
    resetForm();
  };

  const resetForm = () => {
    setEnteredTitle('');
    setEnteredAmount('');
    setEnteredDate('');
  }

  return (
    <form onSubmit={submitHandler}>
      <div className="new-todo__controls">
        <div className="new-todo__control">
          <label>やること</label>
          <input
            type="text"
            value={enteredTitle}
            onChange={titleChangeHandler} />
        </div>
        <div className="new-todo__control">
          <label>予想時間</label>
          <input
            type="number"
            value={enteredAmount}
            onChange={amountChangeHandler} />
        </div>
        <div className="new-todo__control">
          <label>期限</label>
          <input
            type="date"
            value={enteredDate}
            min="2019-01-01"
            max="2022-12-31"
            onChange={dateChangeHandler} />
        </div>
      </div>
      <div className="new-todo__actions">
        <button type="button" onClick={cancelEditingForm}>取消</button>
        <button type="submit">追加</button>
      </div>
    </form>
  );
}

export default NewToDoForm;