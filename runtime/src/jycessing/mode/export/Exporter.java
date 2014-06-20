package jycessing.mode.export;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jycessing.mode.PyEditor;
import jycessing.mode.PythonMode;
import processing.app.Base;
import processing.app.Library;
import processing.app.Sketch;

public class Exporter {
  
  @SuppressWarnings("unused")
  private static void log(final String msg) {
    if (PythonMode.VERBOSE) {
      System.err.println(Exporter.class.getSimpleName() + ": " + msg);
    }
  }
  
  private Sketch sketch;
  private PyEditor editor; // I don't really want to pass this around but there's some functionality I need
  
  public Exporter(PyEditor editor, Sketch sketch){
    this.sketch = sketch;
    this.editor = editor;
  }
  
  public void export() {
    // Do export-common things - work out libraries, etc.
    List<Library> libraries = new ArrayList<Library>();
    Library core = new Library(Base.getContentFile("core"));
    libraries.add(core);
    
    // Now, do this for each platform:
    try {
      new LinuxExport(64, sketch, editor, libraries).export();
    } catch (IOException e) {
      e.printStackTrace();
      editor.statusError("Export failed!");
    }
    
    log("Opening result folder.");
    Base.openFolder(sketch.getFolder());
  }
}
