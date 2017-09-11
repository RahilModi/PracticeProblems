package com.practice;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ArraysAndStrings {


    /********Problem 1 Unique Chars*****************/
    //We can implement unique chars problem using sorting. Once string is sorted check next element of the string and decide.
    //Implemented w/o using any other data structure.
    //Ask about ASCII or unicode strings
    //If ascii shas only 128 characters and extended ascii has 256 so string length greater than this will not has unique letters
    
    static boolean isUniqueChars(String inputStr){
        int i=0;
        if(inputStr.length()==0){
            System.out.println("Empty String..");
            return false;
        }
        if(inputStr.length()>128){
            return false;
        }
        
        boolean bUnique =true;

        while(i<inputStr.length() && bUnique){
            char ch = inputStr.charAt(i);
            for(int k = 0; k<inputStr.length();k++){
                if(i != k && ch == inputStr.charAt(k)){
                    bUnique = false;
                }
            }
            i++;
        }
        return bUnique;
    }

    static boolean isUniqueCharsUsingBooleanArray(String inputStr) {
        int i = 0;
        if (inputStr.length() == 0) {
            System.out.println("Empty String..");
            return false;
        }
        if (inputStr.length() > 128) {
            return false;
        }

        boolean [] array = new boolean[128];

        while (i < inputStr.length()) {
            int val = inputStr.charAt(i);
            if(array[val]){
                return false;
            }
            array[val]=true;
            i++;
        }
        return true;
    }

    //assume string has only lower letters and reduce the extra space by factor of 8
    static boolean isUniqueCharsUsingBitVectors(String inputStr){
        int checker = 0;
        int i=0;
        while(i<inputStr.length()){
            int val = inputStr.charAt(i)-'a';
            //here left shifts 1 to val times and padding would be 0's..
            //if character repeats we will get the same value and it is stored in checker using or operation outside of if block.
            //will prevent them from moving ahead.
            if((checker & (1<<val))>0){
                return false;
            }
            checker |= (1<<val);
            i++;
        }
        return true;

    }


    //Implemented using HashSet
    static boolean isUniqueCharsUsingHashSet(String inputStr){
        int i =0;
        boolean bUnique = true;
        HashSet<Character> setOfletters = new HashSet<>();
        while(i<inputStr.length() && bUnique){
            if(setOfletters.contains(inputStr.charAt(i))){
                bUnique =false;
            }else{
                setOfletters.add(inputStr.charAt(i));
            }
            i++;
        }
        return bUnique;
    }

    /********Problem 1 Unique Chars*****************/

    /********Problem 2 Check one is permutation of other*****************/
    //we should ask case sensitive matters or white space: like "GOD" can be of "dog" or "god   " of "dog" valid scenarios.
    //We have to check if "dog" and "god" is given check permutation of one another.
    //String length of both string must be the same.
    //Sort both strings and then compare character by character.
    //Take an array of fixed 128 characters as we have taken for Problem 1 if string is made up of ASCII letters then make array which has count of letters.
    //For checking string reduce the count and cehck it should be not less than zero. IF SO BOTH STRINGS ARE NOT PERMUTATION OF EACH OTHER..

    static String sort(String str){
        char[] contents = str.toCharArray();
        java.util.Arrays.sort(contents);
        return new String(contents);
    }

    static boolean isPermutationUsingSorting(String inputStr, String checkStr){
        if(inputStr.length() != checkStr.length()){
            return false;
        }
        return sort(inputStr).equals(sort(checkStr));
    }

    static boolean isPermutation(String inputStr, String checkStr){
        if(inputStr.length() != checkStr.length()){
            return false;
        }
        HashMap<Character,Integer> charactCount = new HashMap<>();
        int i =0;
        while(i<inputStr.length()){
            if(charactCount.get(inputStr.charAt(i)) == null){
                charactCount.put(inputStr.charAt(i),1);
            }else{
                charactCount.put(inputStr.charAt(i),charactCount.get(inputStr.charAt(i))+1);
            }
            i++;
        }
        i=0;
        while(i<checkStr.length()){
            if(charactCount.get(checkStr.charAt(i)) == null){
                return false;
            }else{
               if(charactCount.get(checkStr.charAt(i)) == 1){
                   charactCount.remove(checkStr.charAt(i));
               }else{
                   charactCount.put(inputStr.charAt(i),charactCount.get(inputStr.charAt(i))-1);
               }
            }
            i++;
        }
        return true;
    }
    /********Problem 2 Check one is permutation of other*****************/

    /********Problem 3 URLify*****************/
    //Convert given string to URL format: replace whitespace with %20
    //input has given string and its true length;
    static String toURLFormat(String str, int trueLength){
        int numberOfspaces = 0;
        int i =0;
        while(i<trueLength){
            if(str.charAt(i) == ' '){
                numberOfspaces++;
            }
            i++;
        }
        //Here numberOfspaces multiplied by 2 truelength contains already numberOfspaces and we need to replace whitespace with 3 characters
        //so we have multiplied it with 2.
        int lengthOfChararray = trueLength + numberOfspaces*2;
        char[] newChararray = new char[lengthOfChararray];
        i=0;
        int pos=0;
        while(i<trueLength){
            if(str.charAt(i)==' '){
                newChararray[pos]='%';
                newChararray[++pos]='2';
                newChararray[++pos]='0';
            }else{
                newChararray[pos]=str.charAt(i);
            }
            i++;
            pos++;
        }
        return new String(newChararray);
    }
    /********Problem 3 URLify*****************/

    /********Problem 4 Permutation is Palindrome*****************/
    //Input tact coa
    //Ouput : true (permutation: "atco cta" / "taco cat"
    //For this type of problem it is not required to generate all permutations of given
    //Permutation is rearrangement of letters of the same length
    //Palindrome can be verified by using number of times character repeats
    //If String length is EVEN: count of all characters should be even to be a palindrome"
    //If String length is ODD: count of only single characters should be odd to be a palindrome permutation
    //If any thing of the above is not satisfied given strings permutation can not be a palindrome

    static boolean checkMaxOneOdd(HashMap<Character,Integer> charCount){
        int numberOfOdds = 0;
        int numberOfEvens = 0;
        Set<Character> keys = charCount.keySet();
        for(Character ch:keys) {
            if (charCount.get(ch) % 2 == 1) {
                numberOfOdds++;
                if (numberOfOdds > 1) {
                    return false;
                }
            } else {
                numberOfEvens++;
            }
        }
        return true;
    }
    static boolean checkPermutationIsPalindrome(String inputStr){
        HashMap<Character,Integer> characterCount = new HashMap<>();
        int i = 0;
        inputStr = inputStr.toLowerCase();
        while(i<inputStr.length()){
            if(Character.isLetter(inputStr.charAt(i)) || Character.isDigit(inputStr.charAt(i))) {
                if (characterCount.get(inputStr.charAt(i)) == null) {
                    characterCount.put(inputStr.charAt(i), 1);
                } else {
                    int count = characterCount.get(inputStr.charAt(i));
                    characterCount.put(inputStr.charAt(i), ++count);
                }
            }
            i++;
        }
        return checkMaxOneOdd(characterCount);
    }


    /********Problem 4 Permutation is Palindrome*****************/

    /********Problem 5 One Edit Away*****************/
    //On string we can perform insert, remove and update a character.
    //Check whether given strings are one edit or zero edit away
    //Keep to indexes for two strings..check difference.. if found increment only larger index shorter index will remain the same
    //If both characters are the same pointed by indexes increment both.

    static boolean checkOneEditAway(String inputString,String editedStr){
        if(Math.abs(inputString.length() - editedStr.length()) >1){
            return false;
        }

        //get shorter and longer string
        String str1 = (inputString.length()>editedStr.length())?editedStr:inputString;  //str1 will be shorter if both are not the same
        String str2 = (inputString.length()>editedStr.length())?inputString:editedStr; //str2 will be larger
        //indexes for strings
        int index1=0; //points to shorter
        int index2=0; //points to larger
        boolean boneEditPerformedFound = false;
        while(index1<str1.length() && index2<str2.length()){
            if(str1.charAt(index1) != str2.charAt(index2)){
                if(boneEditPerformedFound){
                    return false;
                }
                boneEditPerformedFound = true;
                if(str1.length() == str2.length()){
                    index1++; //both are the same so increment assumed shorter.
                }
            }else{
                index1++; //move shorter string
            }
            index2++; //Always move larger string
        }
        return true;
    }

    /********Problem 5 One Edit Away*****************/
    /********Problem 6 String Compression*****************/
    //Perform string compression
    //Here aabbccddaaa => a2b2c2d2a3
    // abcddeeaasssss => abcd2e2a2s5

    static String compressString(String inputStr){
        int charCount = 1;
        int i = 1;
        StringBuilder strbld = new StringBuilder();
        strbld.append(inputStr.charAt(0));
        while(i<inputStr.length()){
            if(inputStr.charAt(i-1) == inputStr.charAt(i)){
                charCount++;
            }else{
                if(charCount!=1)
                    strbld.append(charCount);
                strbld.append(inputStr.charAt(i));
                charCount=1;
            }
            i++;
        }
        if(charCount != 1) {
            strbld.append(charCount);
        }
        return strbld.toString();
    }
    /********Problem 6 String Compression*****************/

    /********Problem 7 & 8  Rotate Matrix and Nullify Matrix****************/
    //  1 2 3                   1 1 1
    //  1 2 3  => 90 rotate =>  2 2 2
    //  1 2 3                   3 3 3

    //  1 2 3                    3 2 1
    //  1 2 3  => 180 rotate =>  3 2 1
    //  1 2 3                    3 2 1
    static void printMatrix(int[][] matrix){
        int N = matrix.length;
        for(int i =0;i<N;i++){
            for(int j =0;j<matrix[i].length;j++){
                System.out.print(matrix[i][j]+ " ");
            }
            System.out.println("\n");
        }
    }
    static void MirrorMatrix(int[][] matrix){
        //if(matrix.length == 0 || (matrix.length != matrix[0].length)) return;
        int N = matrix.length;
        for(int i = 0; i<N;i++){
            int last = matrix[i].length/2;
            for(int j=0;j<last;j++){
                int offset = matrix[i].length-j-1;
                int temp = matrix[i][offset];
                matrix[i][offset]=matrix[i][j];
                matrix[i][j]=temp;
            }
        }
        printMatrix(matrix);

    }

    static void rotateMatrix(int[][] matrix){

        if(matrix.length == 0 || (matrix.length != matrix[0].length)){
            System.out.println("Matrix must have same length and height");
            return;
        }
        int n = matrix.length;
        for(int i=0;i<n/2;i++){
            int first = i;
            int last = matrix.length - i - 1;

            for(int k = first;k<last;k++){
                int offset = k-first;

                int top = matrix[first][k];

                //bottom-left -> top-left
                matrix[first][k] = matrix[last-offset][first];

                //bottom-right -> bottom-left
                matrix[last-offset][first] = matrix[last][last-offset];

                //top-right -> bottom-right
                matrix[last][last-offset] = matrix[k][last];

                //top-left -> top-right
                matrix[k][last] = top;
            }
        }
        printMatrix(matrix);

    }

    // 1 2 3      1 0 3
    // 1 0 4  =>  0 0 0
    // 5 2 4      5 0 4
    static void nullifyMatrix(int[][] matrix){
        boolean [] rows = new boolean[matrix.length];
        boolean [] cols = new boolean[matrix[0].length];

        for(int i = 0; i < matrix.length ; i++){
            for(int j = 0; j< matrix.length; j++){
                if(matrix[i][j] == 0){
                    rows[i] = true;
                    cols[j] = true;
                }
            }
        }

        for(int i = 0 ; i < rows.length ; i++){
            if(rows[i])
                nullifyRows(matrix,i);
        }

        for(int i = 0 ; i < cols.length ; i++){
            if(cols[i])
                nullifyCols(matrix,i);
        }

        printMatrix(matrix);
    }

    static void nullifyRows(int[][]matrix,int Row){
        for(int j = 0;j<matrix[0].length;j++){
            matrix[Row][j] = 0;
        }
    }

    static void nullifyCols(int[][]matrix,int Col){
        for(int j = 0;j<matrix.length;j++){
            matrix[j][Col] = 0;
        }
    }
    /********Problem 7 & 8  Rotate Matrix and Nullify Matrix****************/

    /***************Problem 9 String Rotation Check******************/
    //Check whether given two strings is rotated string of the other.
    //Using only calling isSubstring function once.
    //if s2 is rotation of s1
    //Assume s1="waterbottle" => x="wat" and y="erbottle" => xy
    // s2 ="erbottlewat" => yx
    // yx will be substring of xyxy => same for s2 => s1s1;

    static boolean isSubstring(String str, String subString){

        if(subString.length() > str.length()){
            return false;
        }
        int index1 = 0;
        int index2 = 0;
        boolean bSubStringFound = false;
        while(index1 < str.length()){
            if(str.charAt(index1) != subString.charAt(index2)){
                index1++;
            }else{
                int len = subString.length();
                int temp = index1;
                while(len > 0){
                    if(subString.charAt(index2) != str.charAt(index1)){
                        index1 = ++temp;
                        index2 = 0;
                        break;
                    }
                    len--;
                    index1++;
                    index2++;
                }
                if(len == 0){
                    bSubStringFound = true;
                    break;
                }
            }
        }
        return bSubStringFound;
    }

    static boolean isRotatedVersion(String inputStr, String rotatedStr){
        if(inputStr.length() == rotatedStr.length() && inputStr.length()>0){
            String str = inputStr + inputStr;
             return isSubstring(str,rotatedStr);
        }else{
            return false;
        }
    }
    /***************Problem 9 String Rotation Check******************/
    public static void main(String[] args) {
	// write your code here
        String str = new String("Helo");
        boolean uniqueCheck = isUniqueChars(str);
        System.out.println(str+ " is unique?" + uniqueCheck);
        str="";
        System.out.println("\"\" is unique?"+ isUniqueChars(str));
        System.out.println("HIIII is unique? "+ isUniqueChars("HIIII"));

        System.out.println("\nHIIII is unique? "+isUniqueCharsUsingHashSet("HIIII"));
        System.out.println("Hey is unique? "+isUniqueCharsUsingHashSet("Hey"));
        System.out.println("Helomih is unique? "+isUniqueCharsUsingHashSet("Helomih"));

        System.out.println("\nHIIII is unique? "+isUniqueCharsUsingBooleanArray("HIIII"));
        System.out.println("Hey is unique? "+isUniqueCharsUsingBooleanArray("Hey"));
        System.out.println("Helomih is unique? "+isUniqueCharsUsingBooleanArray("Helomih"));

        System.out.println("hisedsdsda is unique? "+isUniqueCharsUsingBitVectors("hisedsdsda"));

        System.out.println("\nIs dog permutation of god ?"+isPermutation("god","dog"));
        System.out.println("Is Dog permutation of god ?"+isPermutation("god","Dog"));
        System.out.println("Is dog permutation of good ?"+isPermutation("good","dog"));

        String str1="god";
        String str2="dog";

        System.out.println("\nIs dog permutation of god ?"+isPermutationUsingSorting(str1,str2));
        System.out.println("Is Dog permutation of god ?"+isPermutationUsingSorting("god","Dog"));
        System.out.println("Is dog permutation of good ?"+isPermutationUsingSorting("good","dog"));

        System.out.println("MR John Smith   url version is :"+toURLFormat("MR John Smith   ",13));

        System.out.println("\ntact aco permutation is palindrome ? " + checkPermutationIsPalindrome("tact aco"));
        System.out.println("ab ab permutation is palindrome ? " + checkPermutationIsPalindrome("ab ab"));
        System.out.println("aabbddc permutation is palindrome ? " + checkPermutationIsPalindrome("aabbddc"));


        System.out.println("\n Check two strings are one edit away ? pale, ple " + checkOneEditAway("pale","ple"));
        System.out.println("Check two strings are one edit away ? pales, pale " + checkOneEditAway("pales","pale"));
        System.out.println("Check two strings are one edit away ? pale, bale " + checkOneEditAway("pale","bale"));
        System.out.println("Check two strings are one edit away ? pale, pakes " + checkOneEditAway("pale","pakes"));

        System.out.println("aabbccdddaaa compressed string ? " +compressString("aabbccdddaaa"));

        System.out.println("abcddeeaasssss compressed string ? " +compressString("abcddeeaasssss"));

        System.out.println("Input Matrix:\n");
        int[][] matrix ={{1,2,3},{1,2,3},{1,2,3}};
        printMatrix(matrix);
        System.out.println("180 Degree rotated matrix:\n");
        MirrorMatrix(matrix);
        System.out.println("Input Matrix:\n");
        int[][]matrix1 ={{1,2,3},{4,5,6},{7,8,9}};
        printMatrix(matrix1);
        System.out.println("180 Degree rotated matrix:\n");
        MirrorMatrix(matrix1);
        System.out.println("Input Matrix:\n");
        int[][]matrix2 ={{1,2,3,4},{1,5,6,3},{1,2,7,8}};
        printMatrix(matrix2);
        System.out.println("180 Degree rotated matrix:\n");
        MirrorMatrix(matrix2);
        System.out.println("Input Matrix:\n");
        int[][]matrix3 ={{1,2,3,4},{1,5,6,3},{1,2,7,8},{9,10,22,33}};
        printMatrix(matrix3);
        System.out.println("180 Degree rotated matrix:\n");
        MirrorMatrix(matrix3);

        System.out.println("Input Matrix:\n");
        int[][] matrix4 ={{1,2,3},{1,2,3},{1,2,3}};
        printMatrix(matrix4);
        System.out.println("90 Degree rotated matrix:\n");
        rotateMatrix(matrix4);
        System.out.println("Input Matrix:\n");
        int[][]matrix5 ={{1,2,3},{4,5,6},{7,8,9}};
        printMatrix(matrix5);
        System.out.println("90 Degree rotated matrix:\n");
        rotateMatrix(matrix5);
        System.out.println("Input Matrix:\n");
        int[][]matrix6 ={{1,2,3,4},{1,5,6,3},{1,2,7,8}};
        printMatrix(matrix6);
        System.out.println("90 Degree rotated matrix:\n");
        rotateMatrix(matrix6);
        System.out.println("Input Matrix:\n");
        int[][]matrix7 ={{1,2,3,4},{1,5,6,3},{1,2,7,8},{9,10,22,33}};
        printMatrix(matrix7);
        System.out.println("90 Degree rotated matrix:\n");
        rotateMatrix(matrix7);

        System.out.println("Input Matrix\n");
        int[][]matrix10 = {{1,2,3},{1,0,1},{12,44,55}};
        printMatrix(matrix10);
        System.out.println("Nullified ith row and jth col of matrix if [i][j] has 0\n");
        nullifyMatrix(matrix10);
        System.out.println("Input Matrix\n");
        int[][]matrix11 = {{1,2,3},{1,0,1},{12,44,0}};
        printMatrix(matrix11);
        System.out.println("Nullified ith row and jth col of matrix if [i][j] has 0\n");
        nullifyMatrix(matrix11);
        System.out.println("Input Matrix\n");
        int[][]matrix12 = {{0,2,3},{1,0,1},{12,44,55}};
        printMatrix(matrix12);
        System.out.println("Nullified ith row and jth col of matrix if [i][j] has 0\n");
        nullifyMatrix(matrix12);


        String inStr = "waterbottle";
        System.out.println("\nrbottlewate is rotation of waterbottle?" + isRotatedVersion(inStr,"rbottlewate"));
        inStr = "hello how r u ";
        System.out.println("\no how r u hell is rotation of hellohowru ?" + isRotatedVersion(inStr,"o how r u hell"));
        System.out.println("\no hOW r u hell is rotation of hellohowru ?" + isRotatedVersion(inStr,"o hOW r u hell"));

    }
}
