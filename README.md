### Advanced Mortars

This mod is designed for modpack makers. It contains only example recipes and is intended to be used with CraftTweaker. JEI integration is supported, however item transfer functionality has been intentionally omitted.

#### Overview

![Mortars](https://raw.githubusercontent.com/codetaylor/advancedmortars/master/assets/mortars.png)

This mod contains four in-world mortars, each with two recipe modes: mixing and crushing. Each mortar also has a configurable durability.

![Mortar Items](https://raw.githubusercontent.com/codetaylor/advancedmortars/master/assets/items.png)

#### Usage

##### Modes

Switch the mortar between the crushing and mixing mode by sneaking and left-clicking an empty mortar with an empty hand.

![Modes](https://raw.githubusercontent.com/codetaylor/advancedmortars/master/assets/gifs/modes.gif)

##### Crushing

Place up to a stack of items in the mortar by left-clicking the mortar while holding the items. Then, perform the craft by left-clicking or holding down the left mouse button with an empty hand.

![Crushing](https://raw.githubusercontent.com/codetaylor/advancedmortars/master/assets/gifs/crushing.gif)

##### Mixing

Place up to eight individual items in the mortar by left-clicking the mortar while holding the items. Then, perform the craft by left-clicking or holding down the left mouse button with an empty hand. 

![Mixing](https://raw.githubusercontent.com/codetaylor/advancedmortars/master/assets/gifs/mixing.gif)

In mixing mode, the mortar can hold up to eight individual items.

![Mixing2](https://raw.githubusercontent.com/codetaylor/advancedmortars/master/assets/gifs/mixing2.gif)

##### Pick-Up

The mortar can easily be picked up and moved by sneaking and right-clicking the mortar. When picked up this way, the mortar will retain its contents.

![Pick-Up](https://raw.githubusercontent.com/codetaylor/advancedmortars/master/assets/gifs/pickup.gif)

#### Recipes

Four crafting recipes have been added, one for each mortar.

![Crafting Recipes](https://raw.githubusercontent.com/codetaylor/advancedmortars/master/assets/recipes0.png)

Two example recipes for each mortar are also included. The example recipes are intended for modpack makers to quickly grab the mod and test its functionality without having to first supply recipes via CraftTweaker and ZenScript. These recipes can be quickly and easily disabled in the config.

![JEI Integration](https://raw.githubusercontent.com/codetaylor/advancedmortars/master/assets/jei_integration.png)

#### Configuration

The configuration file, `advancedmortars.module.Mortar.cfg`, contains the following:

```
# Configuration file

mortar {

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

    recipes {
        B:ENABLE_DEFAULT_RECIPES=true
    }

}
```

#### CraftTweaker

```
import mods.advancedmortars.Mortar;
```

##### Crushing

Crushing recipes support one input and one output.

```
Mortar.addCrushingRecipe(String[] mortarTypes, IItemStack output, int duration, IIngredient input);
```

Example:

```
Mortar.addCrushingRecipe(["iron", "stone"], <minecraft:gravel>, 20, <minecraft:cobblestone> * 4);
```

This example adds a crushing recipe for both the `iron` and `stone` mortars to crush four cobblestone into one gravel using twenty clicks.

##### Mixing

Mixing recipes support one output and up to eight inputs.

```
Mortar.addMixingRecipe(String[] mortarTypes, IItemStack output, int duration, IIngredient[] inputs);
```

Example:

```
Mortar.addMixingRecipe(["diamond"], <minecraft:dirt>, 1, [<minecraft:red_flower:0>, <ore:logWood>]);
```

This example adds a mixing recipe for the `diamond` mortar to mix a red flower with any `logWood` to produce a single dirt with one click.