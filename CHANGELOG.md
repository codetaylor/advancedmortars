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