#### ZenScript

##### Import

```
import mods.advancedmortars.Mortar;
```

##### Single Item Output

Syntax:

Allows up to eight input ingredients.

```
Mortar.addRecipe(String[] mortarTypes, IItemStack output, int duration, IIngredient[] inputs);
```

Example:

```
Mortar.addRecipe(["iron", "stone"], <minecraft:gravel>, 20, [<minecraft:cobblestone> * 4]);
```

##### Optional Secondary Item

Allows up to eight input ingredients.

Syntax:

```
Mortar.addRecipe(String[] mortarTypes, IItemStack output, int duration, IItemStack secondaryOutput, float secondaryOutputChance, IIngredient[] inputs);
```

Example:

```
Mortar.addRecipe(["wood", "stone", "iron", "diamond"], <minecraft:flint>, 4, <minecraft:stick>, 0.5, [<minecraft:gravel>]);
```