```
# Configuration file

general {

    recipes {
        # Set to false to disable all default recipes.
        B:ENABLE_DEFAULT_RECIPES=true

        # Amount of exhaustion to charge the player per click required to complete a recipe.
        # Set to 0 to disable.
        # Min: 0.0
        # Max: 40.0
        D:EXHAUSTION_COST_PER_CLICK=0.0

        # Minimum amount of hunger the player needs to operate a mortar.
        # Set to 0 to disable.
        # Min: 0
        # Max: 20
        I:MINIMUM_HUNGER_TO_USE=0
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