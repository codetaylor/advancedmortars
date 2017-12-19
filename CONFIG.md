```
# Configuration file

general {

    recipes {
        # Set to false to disable all default recipes.
        B:ENABLE_DEFAULT_RECIPES=true

        # Chance to inflict hunger per click.
        # Uniform distribution is ensured.
        # For example, setting this to 0.25 will apply the hunger cost every four clicks.
        # Set to 0 to disable.
        # Min: 0.0
        # Max: 1.0
        D:HUNGER_COST_CHANCE=0.0

        # Amount of hunger to charge the player per click required to complete a recipe.
        # Set to 0 to disable.
        # Min: 0
        # Max: 20
        I:HUNGER_COST_PER_CLICK=0
    }

    durability {
        # Set to 0 for infinite uses.
        # Min: 0
        # Max: 2147483647
        I:DIAMOND=1024

        # Set to 0 for infinite uses.
        # Min: 0
        # Max: 2147483647
        I:IRON=256

        # Set to 0 for infinite uses.
        # Min: 0
        # Max: 2147483647
        I:STONE=64

        # Set to 0 for infinite uses.
        # Min: 0
        # Max: 2147483647
        I:WOOD=16
    }

}
```