#### ZenScript

```
import mods.advancedmortars.Mortar;

// Syntax
//
// Mortar.addRecipe(String[] mortarTypes, IItemStack output, int duration, IIngredient[] inputs);

// Example
Mortar.addRecipe(["iron", "stone"], <minecraft:gravel>, 20, [<minecraft:cobblestone> * 4]);
```