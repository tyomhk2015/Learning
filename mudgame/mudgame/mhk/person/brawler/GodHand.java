package com.mudgame.mhk.person.brawler;

// Special job for the awakened player.
public interface GodHand {
    public void getSkill(); // Store the skills
    public float useSkill(String skill_name); // Literally, use the stored skills.
}
