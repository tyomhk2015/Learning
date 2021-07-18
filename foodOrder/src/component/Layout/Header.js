import React, { Fragment } from 'react'
import mealsImage from '../../assets/meals.webp';
import styles from './Header.module.css';
import HeaderCartButton from './HeaderCartButton';

const Header = (props) => {
  return (
    <Fragment>
      <header className={styles.header}>
        <h1>バイキング注文</h1>
        <HeaderCartButton onClick={props.onShowCart} />
      </header>
      <div className={styles['main-image']}>
        <img src={mealsImage} alt="準備されておる美味しそうな料理"/>
      </div>
    </Fragment>
  )
}

export default Header;
