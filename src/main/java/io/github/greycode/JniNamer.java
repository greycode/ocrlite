package io.github.greycode;

import java.io.File;
import java.util.logging.Logger;

import static java.util.logging.Level.WARNING;

public class JniNamer {
  private static final Logger log = Logger.getLogger(JniNamer.class.getName());

  public static String getJniName(String stem) {
    String arch = arch();
    String abi = abi(arch);
    String os = os();
    String extension = extension(os);
    return stem + "-" + os + "-" + arch + abi + "." + extension;
  }

  private static String arch() {
    String arch = System.getProperty("os.arch", "").toLowerCase();
    if (arch.equals("x86") || arch.equals("i386") || arch.equals("i486") ||
      arch.equals("i586") || arch.equals("i686"))
      return "i686";
    if (arch.equals("x86_64") || arch.equals("amd64"))
      return "x86_64";
    if (arch.equals("ia64"))
      return "ia64";
    if (arch.equals("arm"))
      return "arm";
    if (arch.equals("armv5l"))
      return "armv5l";
    if (arch.equals("armv6l"))
      return "armv6l";
    if (arch.equals("armv7l"))
      return "armv7l";
    if (arch.equals("sparc"))
      return "sparc";
    if (arch.equals("sparcv9"))
      return "sparcv9";
    if (arch.equals("pa_risc2.0"))
      return "risc2";
    if (arch.equals("ppc"))
      return "ppc";
    if (arch.startsWith("ppc"))
      return "ppc64";

    log.warning("unrecognised architecture: " + arch);
    return "unknown";

  }

  // alternative: https://github.com/sgothel/gluegen/blob/master/src/java/jogamp/common/os/PlatformPropsImpl.java#L211
  private static String abi(String arch) {
    if (!arch.startsWith("arm"))
      return "";
    try {
      // http://docs.oracle.com/javase/tutorial/deployment/doingMoreWithRIA/properties.html
      for (String prop : new String[]{"sun.boot.library.path", "java.library.path", "java.home"}) {
        String value = System.getProperty(prop, "");
        log.config(prop + ": " + value);
        if (value.matches(".*(gnueabihf|armhf).*"))
          return "hf";
      }
      for (String dir : new String[]{"/lib/arm-linux-gnueabihf", "/usr/lib/arm-linux-gnueabihf"}) {
        File file = new File(dir);
        if (file.exists())
          return "hf";
      }
      return "";
    } catch (SecurityException e) {
      log.log(WARNING, "unable to detect ABI", e);
      return "unknown";
    }
  }

  private static String os() {
    String os = System.getProperty("os.name", "").toLowerCase();
    if (os.startsWith("linux"))
      return "linux";
    if (os.startsWith("windows"))
      return "win";
    if (os.startsWith("mac os x") || os.startsWith("darwin"))
      return "osx";
    if (os.startsWith("freebsd"))
      return "freebsd";
    if (os.startsWith("android"))
      return "android";
    if (os.startsWith("sunos"))
      return "sun";
    if (os.startsWith("hp-ux"))
      return "hpux";
    if (os.startsWith("kd"))
      return "kd";
    log.warning("unable to detect OS type: " + os);
    return "unknown";
  }

  private static String extension(String os) {
    if (os.equals("win"))
      return "dll";
    if (os.equals("osx"))
      return "jnilib";
    return "so";
  }
}
