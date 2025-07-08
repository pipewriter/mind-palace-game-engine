
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;
import java.util.*;

import java.awt.datatransfer.*;

import java.awt.*;


import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
import java.awt.image.*;
import java.io.IOException;
class TransferableImage implements Transferable, ClipboardOwner {

    Image i;

    public TransferableImage( Image i ) {
        this.i = i;
    }

    //empty ClipBoardOwner implementation
    public void lostOwnership(Clipboard clipboard, Transferable contents) {}

    public Object getTransferData( DataFlavor flavor )
    throws UnsupportedFlavorException, IOException {
        if ( flavor.equals( DataFlavor.imageFlavor ) && i != null ) {
            return i;
        }
        else {
            throw new UnsupportedFlavorException( flavor );
        }
    }

    public DataFlavor[] getTransferDataFlavors() {
        DataFlavor[] flavors = new DataFlavor[ 1 ];
        flavors[ 0 ] = DataFlavor.imageFlavor;
        return flavors;
    }

    //unused
    public boolean isDataFlavorSupported( DataFlavor flavor ) {
        DataFlavor[] flavors = getTransferDataFlavors();
        for ( int i = 0; i < flavors.length; i++ ) {
            if ( flavor.equals( flavors[ i ] ) ) {
                return true;
            }
        }

        return false;
    }
}