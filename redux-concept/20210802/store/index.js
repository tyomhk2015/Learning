import { createSlice, configureStore } from '@reduxjs/toolkit';


const initialState = { counter: 0, isShow: true };

const counterSlice = createSlice({
  // name of this slice, the state.
  name: 'counter',
  // Initial state info.
  initialState,
  // All the reducers (functions) this, the counter, slice is going to use.
  reducers: {
    // Automatically recieves the latest state, thanks to the reduxjs toolkit.
    increment(latestState) {
      latestState.counter++;
    },
    decrement(latestState) {
      latestState.counter--;
    },
    increase(latestState, action) {
      latestState.counter = latestState.counter + action.payload;
    },
    toggle(latestState) {
      latestState.isShow = !latestState.isShow;
    }
  }
});


const store = configureStore({
  // For singular reducer
  reducer: counterSlice.reducer
  
  // For multiple reducers
  // reducer: { counter: counterSlice.reducer}
});

// Dispatch actions for the slice, the counterSlice in this case.
// The createSlice automatically creates 'actions' with unique identifiers per action. (Behind the scene workflow)
// Basically, this means that the createSlice does some job for the developers.
export const counterActions = counterSlice.actions;

export default store;
