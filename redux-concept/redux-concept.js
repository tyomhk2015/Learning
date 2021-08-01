const redux = require('redux');

// Reducer, a function, that will mutate the state.
const reducer = (oldState = { message: '' }, action) => {
  if (action.type === 'ADD') {
    return {
      // Adds 'Added' string to the message property.
      message: oldState.message + action.payload
    }
  }
  if (action.type === 'REMOVE') {
    return {
      // Removes 'Added' string from the message property.
      message: oldState.message.replace('Added','')
    }
  }

  // If there is no designated action type, return the old state.
  return oldState;
};

// Tie redux to centeral store data.
const store = redux.createStore(reducer);

// Make a function that will subscribe to the centeral store data.
const subscriber = () => {
  const latestState = store.getState();
  console.log('Latest state : ' + latestState.message);
};

// Tie the function to the centeral store data by subscribing to it, the store data.
store.subscribe(subscriber);

// Set dispatcher for requesting mutation of the state in the centeral store.
// Note that the mutation request is invoked when 'dispatch' has been read by the computer.
store.dispatch({ type: 'ADD', payload: 'Added'});
store.dispatch({ type: 'REMOVE', payload: 'Removed'});

