package com.mudgame.mhk.skilldata;

import java.util.HashMap;

public class SkillData {

    private HashMap<String, Float> skills = new HashMap<String, Float>(); // All the available skills.

    public SkillData() {
        this.skills.put("Mega punch", 1.25f); // When 'Mega Punch' is used, inflict 150% damage of the given attack_point.
        this.skills.put("Jump kick", 1.50f); // When 'Jump Kick' is used, inflict 175% damage of the given attack_point.
        this.skills.put("Ground pound", 1.75f); // When 'Ground Pound' is used, inflict 200% damage of the given attack_point.
        this.skills.put("Roar", 1.25f); // When 'Roar' is used, increase the attack_point by 150% for three turns.
    }

    public HashMap<String, Float> getSkills() {
        return skills;
    }
}
