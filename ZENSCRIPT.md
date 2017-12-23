### ZenScript

#### Import

```
import mods.advancedmortars.Mortar;
```

#### Single Item Output

##### Syntax:

* *Maximum of eight input ingredients.*
* *Each input ingredient must be different.* Using the same ingredient multiple times in the input array will **not** yield the desired results.

```
Mortar.addRecipe(String[] mortarTypes, IItemStack output, int duration, IIngredient[] inputs);
```

##### Examples:

```
Mortar.addRecipe(["iron", "stone"], <minecraft:gravel>, 20, [<minecraft:cobblestone> * 4]);
```

This recipe for the `iron` and `stone` mortar will use `4` `cobblestone`, take `20` clicks, and produce `1` `gravel`.

```
Mortar.addRecipe(["diamond"], <minecraft:gravel> * 2, 5, [<minecraft:cobblestone>, <minecraft:flint> * 2]);
```

This recipe for the `diamond` mortar will use `1` `cobblestone` and `2` `flint`, take `5` clicks, and produce `2` `gravel`. 

#### Optional Secondary Item Output

##### Syntax:

* *Maximum of eight input ingredients.*
* *Each input ingredient must be different.* Using the same ingredient multiple times in the input array will **not** yield the desired results.

```
Mortar.addRecipe(String[] mortarTypes, IItemStack output, int duration, IItemStack secondaryOutput, float secondaryOutputChance, IIngredient[] inputs);
```

##### Examples:

```
Mortar.addRecipe(["wood", "stone", "iron", "diamond"], <minecraft:flint>, 4, <minecraft:flint> * 2, 0.5, [<minecraft:gravel> * 3]);
```

This recipe for all mortars will use `3` `gravel`, take `4` clicks, and produce `1` `flint` with a `50%` chance to produce an additional `2` `flint`.