import React from 'react';
import styles from './AvailableMeals.module.css'
import MealItem from './MealItem/MealItem';
import Card from '../UI/Card';

const DUMMY_MEALS = [
  {
    id: 'm1',
    name: 'すし',
    description: 'マグロ、エビ、うなぎ、だし巻き卵、さば、サーモン、いくら',
    price: 2100,
  },
  {
    id: 'm2',
    name: 'とんかつ',
    description: '特製タレをつけたやわらかいジューシーなとんかつ（300g）',
    price: 1600,
  },
  {
    id: 'm3',
    name: 'BBQバーガー',
    description: 'アメリカ式で調理したバーガー（300g）',
    price: 1500,
  },
  {
    id: 'm4',
    name: 'サラダー',
    description: '体を元気にするために良い！5種類のドレッシングを提供しております！',
    price: 1800,
  },
  {
    id: 'm5',
    name: 'ラーメン',
    description: '濃い豚骨の味がする人気のラーメン！',
    price: 1200,
  },
];

const AvailableMeals = () => {
  const mealsList = DUMMY_MEALS.map((meal) => 
    <MealItem 
      id= {meal.id}
      key={meal.id}
      name={meal.name}
      description={meal.description}
      price={meal.price}
    />
  );
  return (
    <section className={styles.meals}>
      <Card>
        <ul>
          {mealsList}
        </ul>
      </Card>
    </section>
  )
};

export default AvailableMeals;
