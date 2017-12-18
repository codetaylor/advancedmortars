import mods.advancedmortars.Mortar;

Mortar.addCrushingRecipe(["wood"], <minecraft:sand>, 5, <minecraft:gravel>);

Mortar.addCrushingRecipe(["iron", "stone"], <minecraft:gravel>, 20, <minecraft:cobblestone> * 4);

Mortar.addMixingRecipe(["diamond"], <minecraft:dirt> * 64, 64, [<minecraft:redstone>, <minecraft:redstone>, <minecraft:redstone>, <minecraft:redstone>, <minecraft:redstone>, <minecraft:redstone>, <minecraft:red_flower:0>, <ore:logWood>]);
Mortar.addMixingRecipe(["diamond"], <minecraft:dirt> * 32, 32, [<minecraft:redstone>, <minecraft:redstone>, <minecraft:redstone>, <minecraft:redstone>, <minecraft:redstone>, <minecraft:red_flower:0>, <ore:logWood>]);
Mortar.addMixingRecipe(["diamond"], <minecraft:dirt> * 16, 16, [<minecraft:redstone>, <minecraft:redstone>, <minecraft:redstone>, <minecraft:redstone>, <minecraft:red_flower:0>, <ore:logWood>]);
Mortar.addMixingRecipe(["diamond"], <minecraft:dirt> * 8, 8, [<minecraft:redstone>, <minecraft:redstone>, <minecraft:redstone>, <minecraft:red_flower:0>, <ore:logWood>]);
Mortar.addMixingRecipe(["diamond"], <minecraft:dirt> * 4, 4, [<minecraft:redstone>, <minecraft:redstone>, <minecraft:red_flower:0>, <ore:logWood>]);
Mortar.addMixingRecipe(["diamond"], <minecraft:dirt> * 2, 2, [<minecraft:redstone>, <minecraft:red_flower:0>, <ore:logWood>]);
Mortar.addMixingRecipe(["diamond"], <minecraft:dirt>, 1, [<minecraft:red_flower:0>, <ore:logWood>]);
Mortar.addCrushingRecipe(["diamond"], <minecraft:gravel> * 2, 20, <minecraft:cobblestone>);
Mortar.addCrushingRecipe(["diamond"], <minecraft:sand> * 2, 20, <minecraft:gravel>);
