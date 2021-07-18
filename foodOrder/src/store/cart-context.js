import { createContext } from "react";

const CartContext = createContext({
  items: [], // Managing cart items.
  totalAmount: 0,
  // functions for updating context
  addItem: (item) => {},
  removeItem: (id) => {},
});

export default CartContext;
