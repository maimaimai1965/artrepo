package ua.mai.art.module;

import org.springframework.beans.factory.annotation.Autowired;
import ua.telesens.plu.module.LogUtilsInfo;
import ua.telesens.plu.module.ModuleInfo;
import ua.telesens.plu.module.PluCommonInfo;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Класс, описывающий модуль {@value #MODULE_NAME}.
 */
/*
 * (c) Copyright 2018 Telesens. ALL RIGHTS RESERVED.
 *
 * Tinterconnect v.3.2
 *
 * Module: OracleUtils
 *
 * $URL: http://svn-plu.hq.telesens.lan/tibs/JIC3.2.2/trunk/08.Java_Tools/03.Modules/OracleUtils/src/main/java/ua/telesens/plu/module/OracleUtilsInfo.java $
 * $Author: amiroshnik $
 * $Date:: 2019-05-08 12:47:54Z      $
 * $Revision: 63651 $
 */
public class ArtModuleInfo implements ModuleInfo {

  /** Имя модуля. */
  public final static String MODULE_NAME = "art";

  public final static String MODULE_PATH_NO = "";

  private final static ArtModuleInfo moduleInfo = new ArtModuleInfo();


  private ArtModuleInfo() {}

  public static ArtModuleInfo getInstance() {
    return moduleInfo;
  }
  
  @Override
  public String getModuleName() {
    return MODULE_NAME;
  }

  @Override
  public String getPatch_no() {
    return MODULE_PATH_NO;
  };

  /** Используемые модули. */
  private static final ArrayList<ModuleInfo> usedModules = new ArrayList<>(Arrays.asList(
      LogUtilsInfo.getInstance(),
      PluCommonInfo.getInstance()
  ));

  @Override
  public ArrayList<ModuleInfo> getUsedModules() {
    return usedModules;
  }

  public static void main(String... args) {
    moduleInfo.printModuleInfo();
  }

}
