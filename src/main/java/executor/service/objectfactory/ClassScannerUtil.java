package executor.service.objectfactory;

import org.reflections.Reflections;

class ClassScannerUtil {

  private static Reflections CLASS_SCANNER = new Reflections("/src/java/executor/service/");

  public static Reflections getClassScanner(String path) {
    if (path != null && !path.isEmpty()) {
      CLASS_SCANNER = new Reflections(path);
    }
    return CLASS_SCANNER;
  }

}
