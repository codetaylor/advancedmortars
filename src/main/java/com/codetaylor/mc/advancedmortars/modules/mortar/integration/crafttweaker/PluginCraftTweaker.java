package com.sudoplay.mc.pwcustom.modules.mortar.integration.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;

import java.util.LinkedList;
import java.util.List;

public class PluginCraftTweaker {

  public static final List<IAction> LATE_REMOVALS = new LinkedList<>();
  public static final List<IAction> LATE_ADDITIONS = new LinkedList<>();

  public static void init() {

    CraftTweakerAPI.registerClass(ZenMortar.class);
  }

  public static void apply() {

    LATE_REMOVALS.forEach(CraftTweakerAPI::apply);
    LATE_ADDITIONS.forEach(CraftTweakerAPI::apply);
  }
}
