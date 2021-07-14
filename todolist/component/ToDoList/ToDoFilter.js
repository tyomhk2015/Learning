import './ToDoFilter.css';

const ToDoFilter = (props) => {
  const changedYearHandler = (event) => {
    props.onPickedYear(event.target.value);
  }

  return (
    <div className='todos-filter'>
      <div className='todos-filter__control'>
        <label>年度で絞り込む</label>
        <select value={props.selectedYear} onChange={changedYearHandler}>
          <option value='2022'>2022</option>
          <option value='2021'>2021</option>
          <option value='2020'>2020</option>
          <option value='2019'>2019</option>
        </select>
      </div>
    </div>
  );
};

export default ToDoFilter;