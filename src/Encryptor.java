public class Encryptor
{
    /** A two-dimensional array of single-character strings, instantiated in the constructor */
    private String[][] letterBlock;

    /** The number of rows of letterBlock, set by the constructor */
    private int numRows;

    /** The number of columns of letterBlock, set by the constructor */
    private int numCols;

    /** Constructor*/
    public Encryptor(int r, int c)
    {
        letterBlock = new String[r][c];
        numRows = r;
        numCols = c;
    }

    public String[][] getLetterBlock()
    {
        return letterBlock;
    }

    /** Places a string into letterBlock in row-major order.
     *
     *   @param str  the string to be processed
     *
     *   Postcondition:
     *     if str.length() < numRows * numCols, "A" in each unfilled cell
     *     if str.length() > numRows * numCols, trailing characters are ignored
     */
    public void fillBlock(String str)
    {
        String s = str;
        if(str.length() < (numRows * numCols)) {
            while(s.length() < numRows * numCols) {
                s += "A";
            }
        }
        //s += "A";
        int i = 0;
        for(int r = 0; r < numRows; r++) {
            for(int c = 0; c < numCols; c++) {
                letterBlock[r][c] = s.substring(i, i+1);
                i++;
            }
        }
    }

    /** Extracts encrypted string from letterBlock in column-major order.
     *
     *   Precondition: letterBlock has been filled
     *
     *   @return the encrypted string from letterBlock
     */
    public String encryptBlock()
    {
        String returnStr = "";
        for(int c = 0; c < numCols; c++) {
            for(int r = 0; r < numRows; r++) {
                returnStr += letterBlock[r][c];
            }
        }
        return returnStr;
    }

    /** Encrypts a message.
     *
     *  @param message the string to be encrypted
     *
     *  @return the encrypted message; if message is the empty string, returns the empty string
     */
    public String encryptMessage(String message)
    {
        String concatenatedStr = "";
        String m = message;
        while(m.length() > 0) {
            String s = "";
            if(m.length() > numCols * numRows) {
                s = m.substring(0, numRows * numCols);
                m = m.substring(numCols * numRows);
            }
            else{
                s = m;
                m = "";
            }
            fillBlock(s);
            concatenatedStr += encryptBlock();
        }
        return concatenatedStr;
    }

    public String decryptBlock() {
        String returnStr = "";
        int x = 0;
        for(int i = 0; i < numRows * numCols; i++) {
            //System.out.println("x: " + x + " r: " + (x/numRows) + " c : " + (x % numCols));
            returnStr += letterBlock[x / numCols][x % numCols];

            x += numRows;
            if(x >= numRows * numCols) {
                x %= numCols * numRows;
                x++;
            }
        }
        return returnStr;
    }

    /**  Decrypts an encrypted message. All filler 'A's that may have been
     *   added during encryption will be removed, so this assumes that the
     *   original message (BEFORE it was encrypted) did NOT end in a capital A!
     *
     *   NOTE! When you are decrypting an encrypted message,
     *         be sure that you have initialized your Encryptor object
     *         with the same row/column used to encrypted the message! (i.e.
     *         the “encryption key” that is necessary for successful decryption)
     *         This is outlined in the precondition below.
     *
     *   Precondition: the Encryptor object being used for decryption has been
     *                 initialized with the same number of rows and columns
     *                 as was used for the Encryptor object used for encryption.
     *
     *   @param encryptedMessage  the encrypted message to decrypt
     *
     *   @return  the decrypted, original message (which had been encrypted)
     *
     *   TIP: You are encouraged to create other helper methods as you see fit
     *        (e.g. a method to decrypt each section of the decrypted message,
     *         similar to how encryptBlock was used)
     */
    public String decryptMessage(String encryptedMessage)
    {
        String concatenatedStr = "";
        String m = encryptedMessage;
        while(m.length() > 0) {
            String s = "";
            if(m.length() > numCols * numRows) {
                s = m.substring(0, numRows * numCols);
                m = m.substring(numCols * numRows);
            }
            else{
                s = m;
                m = "";
            }
            //System.out.println(s);
            fillBlock(s);
            concatenatedStr += decryptBlock();
        }

        if(concatenatedStr.charAt(concatenatedStr.length() - 1) == 'A') {
            int a = 0;
            for(int i = concatenatedStr.length() - 1; i >= 0; i--) {
                if(concatenatedStr.charAt(i) != 'A') {
                    a = i + 1;
                    break;
                }
            }
            concatenatedStr = concatenatedStr.substring(0, a);
        }

        return concatenatedStr;
    }
}