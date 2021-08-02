import { useSelector, useDispatch } from 'react-redux';
import { counterActions } from '../store/index';
import classes from './Counter.module.css';

const Counter = () => {
  const counter = useSelector((store) => store.counter);
  const isShow = useSelector((store) => store.isShow);
  const dispatch = useDispatch();

  const increment = () => {
    // dispatch({ type: 'increment' });
    dispatch(counterActions.increment());
  };

  const increaseHanlder = () => {
    // dispatch({ type: 'increase', payload: 10 });

    // The parameter of increase() returns the following object.
    // {type: SOME UNIQUE ID, payload: 10}
    dispatch(counterActions.increase(10));
  };

  const decrement = () => {
    dispatch(counterActions.decrement());
  };

  const toggleCounterHandler = () => {
    dispatch(counterActions.toggle());
  };

  return (
    <main className={classes.counter}>
      <h1>Redux Counter</h1>
      {isShow && <div className={classes.value}>{counter}</div>}
      <div>
        <button onClick={increment}>+</button>
        <button onClick={increaseHanlder}>+10</button>
        <button onClick={decrement}>-</button>
      </div>
      <button onClick={toggleCounterHandler}>Toggle Counter</button>
    </main>
  );
};

export default Counter;
