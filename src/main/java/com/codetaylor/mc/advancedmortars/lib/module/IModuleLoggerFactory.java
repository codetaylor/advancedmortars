package com.sudoplay.mc.pwcustom.lib.module;

import org.apache.logging.log4j.Logger;

public interface IModuleLoggerFactory {

  Logger create(IModule module);

}
