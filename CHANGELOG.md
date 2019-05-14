1.6.23
* Fix JEI mouseover bounds (#40)
* Fix empty durability bar displays in JEI

1.6.22
* Added support for CrT IngredientOr (#30)
* Added durability bars on the mortar items (#31)
* Added tr_tr.lang (#34 Emirhangg)

1.6.21
* Fixed: doesn't respect NBT tags (#33)

1.6.20
* Fixed: default recipes causing JEI errors (#29)

1.6.19
* Fixed: secondary output quantity discrepancy

1.6.18
* Fixed: output syntax cannot be null (#27) caused by declaring the main output of the recipe as `null` in the .zs script

1.6.17
* Fixed: recipes with no secondary outputs render empty secondary output in JEI (#26)
* Changed: improved readability of JEI recipes when using resource packs that alter the background texture

1.6.16
* Changed: updated zh_cn.lang (PR#23 DYColdWind)

1.6.15
* Fixed: world load hangs on invalid script (#22)

1.6.14
* Improved usability:
    * Changed how mortar input is handled: items can only be inserted into the mortar if the item being inserted, combined with the items already in the mortar, partially satisfy any recipe.
    * Added config option to require an empty hand to use the mortar, defaults to allowing usage with a full hand.

1.5.14
* Fixed: Oredict recipes not working properly (#21)
* Add extended tooltip info that displays the item retrieval instructions (#19)

1.5.13
* Now hides crosshairs when displaying mortar durability in overlay 

1.5.12
* Fixed gray text gui while looking at mortar bug

1.5.11
* UX redesign with considerable help from Yuudaari
* Added config option to disable ingredient return and mortar pickup UI overlay hints
* Added config option to disable durability display in the UI overlay

1.4.11
* Added requested feature: config option to force mortar to spill contents when moved

1.3.11
* Improved zen method error handling and reporting

1.3.10
* Fixed: Always shows 100% for secondary item drop in JEI

1.3.9
* Fixed NPE crash

1.3.8
* Added requested feature: support for secondary item output with chance

1.2.8
* Fixed recipe update not triggering on item removal
* Removed System.out saturation spam
* Fixed NPE crash

1.2.7
* Removed dependency on MTLib, will now work without ModTweaker. Still requires CraftTweaker.

1.2.6
* NOTICE: ZenScript syntax has changed
* NOTICE: Config file has changed, please refresh
* Removed mortar modes. The behavior of each mode has been merged into a single mode. For more information on this decision, please see [this discussion](https://github.com/codetaylor/advancedmortars/issues/8).
* Changed the hunger mechanic to use exhaustion.
* Updated Forge to version 14.23.1.2578.

1.1.6
* Player's hunger will not go below zero
* Hunger cost will only be applied when recipe progress increments successfully
* Player can't increment recipe progress if they don't have enough hunger to cover the cost

1.1.5
* Slightly modified hunger cost uniform distribution implementation, probably imperceptible to player

1.1.4
* Added requested feature: usage can have optional hunger cost (see config file)

1.0.4
* Fixed config not working

1.0.3
* Fixed server crash
* Fixed stone/iron meta mixup

1.0.0