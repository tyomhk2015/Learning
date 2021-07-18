import React from "react";
import styles from "./MealsSummary.module.css";

const MealsSummary = () => {
  return (
    <section className={styles.summary}>
      <h2>美味しい料理を配達しております！</h2>
      <p>
        近くにあるお店でお好きな料理を注文してみてください！
        <br />
        美味しい料理をいつでも楽しめます！
      </p>
      <p>
        紹介しておるすべてのお店は品質が良い食材を使い、
        <br />
        注文を受けた次第に調理をしております！
      </p>
    </section>
  );
};

export default MealsSummary;
