/**
 * Copyright � 2014 GnowTantra Pvt Ltd.
 * Author: Rahul Bhalerao.
 * 
 * This software is developed for private use only for the licensed clients 
 * for Experilearn Verification Application. The code contained in this file 
 * and any executable binary compiled from this source should not be shared 
 * or distributed to unlicensed parties or used in other applications without 
 * prior permissions from the copyright holders.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS 
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL 
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package gujaratirendering;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

public class IndicTextView extends TextView {

  private Rect mRect;
  private Paint mPaint;

  private String FontPath;

  public void setFontPath(String fp) {
    this.FontPath = fp;
  }

  public IndicTextView(Context context) {
    this(context, null);

  }

  public IndicTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    mRect = new Rect();
    mPaint = new Paint();
    mPaint.setStyle(Paint.Style.STROKE);
    mPaint.setColor(getCurrentTextColor());
  }

  private static final String LOG_TAG = IndicTextView.class.getName();

  Canvas savedCanvas;

  Boolean lock = new Boolean(true);

  public native void drawIndicText(String unicodeText, String fontPath, int xStart, int yBaseLine,
      int charHeight, Boolean lock);

  static {
    // System.loadLibrary("harfbuzz");
    System.loadLibrary("complex-script-rendering");
  }

  public void drawGlyph(int glyphBitmap[][], int x, int y) {
    if (glyphBitmap == null) {
      return;
    }
    int height = glyphBitmap.length;
    if (height == 0) {
      return;
    }
    int width = glyphBitmap[0].length;
    Bitmap tempBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int color = getCurrentTextColor();
        int pixColor = Color.argb(glyphBitmap[i][j], Color.red(color), Color.green(color),
            Color.blue(color));
        tempBitmap.setPixel(j, i, pixColor);

      }
    }
    savedCanvas.drawBitmap(tempBitmap, x, y, mPaint);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    if (!isInEditMode()) {
      int count = getLineCount();
      int charHieght = (int) getTextSize();
      String[] textLines = getText().toString().split("\\n");
      Rect r = mRect;
      // Paint paint = mPaint;
      for (int i = 0; i < count; i++) {
        int baseline = getLineBounds(i, r);
        savedCanvas = canvas;
        String currentText = i < textLines.length ? textLines[i] : "";
        drawIndicText(currentText, FontPath, r.left, baseline, charHieght, lock);
      }
    }
  }
}
