package com.sudoplay.mc.pwcustom.lib.module;

import net.minecraftforge.common.MinecraftForge;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleRegistry {

  private Map<Class<? extends IModule>, IModule> moduleMap;
  private IModuleLoggerFactory moduleLoggerFactory;

  public ModuleRegistry(IModuleLoggerFactory moduleLoggerFactory) {

    this.moduleLoggerFactory = moduleLoggerFactory;
    this.moduleMap = new HashMap<>();
  }

  public void registerModules(IModule... modules) {

    for (IModule module : modules) {
      this.registerModule(module);
    }
  }

  public void registerModule(IModule module) {

    if (this.moduleMap.containsKey(module.getClass())) {
      throw new RuntimeException("Module already registered: " + module.getClass());
    }

    MinecraftForge.EVENT_BUS.register(module);

    module.setLogger(this.moduleLoggerFactory.create(module));

    this.moduleMap.put(module.getClass(), module);
  }

  public <M extends IModule> M getModule(Class<M> moduleClass) {

    //noinspection unchecked
    return (M) this.moduleMap.get(moduleClass);
  }

  public List<IModule> getModules(List<IModule> result) {

    result.addAll(this.moduleMap.values());
    return result;
  }

  public boolean hasModule(Class<? extends IModule> moduleClass) {

    return this.moduleMap.containsKey(moduleClass);
  }
}
