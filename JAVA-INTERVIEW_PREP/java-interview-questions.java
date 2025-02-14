// 1. String Reverse
public String reverseString(String str) {
    if (str == null) return null;
    return new StringBuilder(str).reverse().toString();
}

// 2. Check Prime Number
public boolean isPrime(int n) {
    if (n <= 1) return false;
    for (int i = 2; i <= Math.sqrt(n); i++) {
        if (n % i == 0) return false;
    }
    return true;
}

// 3. Factorial Calculation
public long factorial(int n) {
    if (n < 0) throw new IllegalArgumentException("Negative input");
    if (n <= 1) return 1;
    return n * factorial(n - 1);
}

// 4. Find Second Largest in Array
public int findSecondLargest(int[] arr) {
    if (arr.length < 2) throw new IllegalArgumentException("Array too small");
    int largest = Integer.MIN_VALUE;
    int secondLargest = Integer.MIN_VALUE;
    
    for (int num : arr) {
        if (num > largest) {
            secondLargest = largest;
            largest = num;
        } else if (num > secondLargest && num < largest) {
            secondLargest = num;
        }
    }
    return secondLargest;
}

// 5. Check Armstrong Number
public boolean isArmstrong(int n) {
    int original = n;
    int sum = 0;
    int digits = String.valueOf(n).length();
    
    while (n > 0) {
        int digit = n % 10;
        sum += Math.pow(digit, digits);
        n /= 10;
    }
    return sum == original;
}

// 6. Find Missing Number in Array
public int findMissingNumber(int[] arr, int n) {
    int expectedSum = (n * (n + 1)) / 2;
    int actualSum = 0;
    for (int num : arr) {
        actualSum += num;
    }
    return expectedSum - actualSum;
}

// 7. Check Anagram
public boolean areAnagrams(String str1, String str2) {
    if (str1.length() != str2.length()) return false;
    
    int[] charCount = new int[256];
    for (int i = 0; i < str1.length(); i++) {
        charCount[str1.charAt(i)]++;
        charCount[str2.charAt(i)]--;
    }
    
    for (int count : charCount) {
        if (count != 0) return false;
    }
    return true;
}

// 8. Merge Two Sorted Arrays
public int[] mergeSortedArrays(int[] arr1, int[] arr2) {
    int[] result = new int[arr1.length + arr2.length];
    int i = 0, j = 0, k = 0;
    
    while (i < arr1.length && j < arr2.length) {
        if (arr1[i] <= arr2[j]) {
            result[k++] = arr1[i++];
        } else {
            result[k++] = arr2[j++];
        }
    }
    
    while (i < arr1.length) result[k++] = arr1[i++];
    while (j < arr2.length) result[k++] = arr2[j++];
    
    return result;
}

// 9. Remove Duplicates from Array
public int[] removeDuplicates(int[] arr) {
    return Arrays.stream(arr).distinct().toArray();
}

// 10. Find GCD
public int findGCD(int a, int b) {
    while (b != 0) {
        int temp = b;
        b = a % b;
        a = temp;
    }
    return a;
}

// 11. Check Perfect Number
public boolean isPerfectNumber(int n) {
    if (n <= 1) return false;
    int sum = 1;
    for (int i = 2; i <= Math.sqrt(n); i++) {
        if (n % i == 0) {
            sum += i;
            if (i != n/i) sum += n/i;
        }
    }
    return sum == n;
}

// 12. Binary to Decimal Conversion
public int binaryToDecimal(String binary) {
    return Integer.parseInt(binary, 2);
}

// 13. Find Maximum Occurring Character
public char maxOccurringChar(String str) {
    int[] charCount = new int[256];
    int maxCount = 0;
    char result = ' ';
    
    for (char c : str.toCharArray()) {
        charCount[c]++;
        if (charCount[c] > maxCount) {
            maxCount = charCount[c];
            result = c;
        }
    }
    return result;
}

// 14. Bubble Sort Implementation
public void bubbleSort(int[] arr) {
    for (int i = 0; i < arr.length - 1; i++) {
        for (int j = 0; j < arr.length - i - 1; j++) {
            if (arr[j] > arr[j + 1]) {
                int temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }
    }
}

// 15. Check Power of Two
public boolean isPowerOfTwo(int n) {
    return n > 0 && (n & (n - 1)) == 0;
}

// 16. Find Intersection of Two Arrays
public int[] findIntersection(int[] arr1, int[] arr2) {
    Set<Integer> set1 = Arrays.stream(arr1).boxed().collect(Collectors.toSet());
    return Arrays.stream(arr2)
                 .filter(set1::contains)
                 .distinct()
                 .toArray();
}

// 17. Count Words in String
public int countWords(String str) {
    if (str == null || str.trim().isEmpty()) return 0;
    return str.trim().split("\\s+").length;
}

// 18. Check Leap Year
public boolean isLeapYear(int year) {
    return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
}

// 19. Matrix Addition
public int[][] matrixAdd(int[][] mat1, int[][] mat2) {
    int rows = mat1.length;
    int cols = mat1[0].length;
    int[][] result = new int[rows][cols];
    
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            result[i][j] = mat1[i][j] + mat2[i][j];
        }
    }
    return result;
}

// 20. Check String Rotation
public boolean isRotation(String str1, String str2) {
    return str1.length() == str2.length() && 
           (str1 + str1).contains(str2);
}

// 21. Find LCM
public int findLCM(int a, int b) {
    return (a * b) / findGCD(a, b);
}

// 22. Check Balanced Brackets
public boolean isBalancedBrackets(String str) {
    Stack<Character> stack = new Stack<>();
    Map<Character, Character> brackets = new HashMap<>();
    brackets.put(')', '(');
    brackets.put('}', '{');
    brackets.put(']', '[');
    
    for (char c : str.toCharArray()) {
        if (brackets.containsValue(c)) {
            stack.push(c);
        } else if (brackets.containsKey(c)) {
            if (stack.isEmpty() || stack.pop() != brackets.get(c)) {
                return false;
            }
        }
    }
    return stack.isEmpty();
}

// 23. Sum of Digits
public int sumOfDigits(int n) {
    int sum = 0;
    while (n > 0) {
        sum += n % 10;
        n /= 10;
    }
    return sum;
}

// 24. Find Unique Elements
public List<Integer> findUniqueElements(int[] arr) {
    return Arrays.stream(arr)
                 .boxed()
                 .collect(Collectors.groupingBy(
                     Function.identity(),
                     Collectors.counting()
                 ))
                 .entrySet()
                 .stream()
                 .filter(e -> e.getValue() == 1)
                 .map(Map.Entry::getKey)
                 .collect(Collectors.toList());
}

// 25. Pattern Matching
public boolean matches(String text, String pattern) {
    return Pattern.compile(pattern)
                 .matcher(text)
                 .matches();
}

// 26. Remove Vowels
public String removeVowels(String str) {
    return str.replaceAll("[aeiouAEIOU]", "");
}

// 27. Find First Repeating Element
public int firstRepeatingElement(int[] arr) {
    Set<Integer> seen = new HashSet<>();
    for (int num : arr) {
        if (!seen.add(num)) {
            return num;
        }
    }
    return -1;
}

// 28. Decimal to Binary Conversion
public String decimalToBinary(int decimal) {
    return Integer.toBinaryString(decimal);
}

// 29. Check Palindrome Number
public boolean isPalindromeNumber(int n) {
    if (n < 0) return false;
    int reversed = 0;
    int original = n;
    
    while (n > 0) {
        reversed = reversed * 10 + n % 10;
        n /= 10;
    }
    return original == reversed;
}

// 30. Find Common Elements in Three Sorted Arrays
public List<Integer> findCommonElements(int[] arr1, int[] arr2, int[] arr3) {
    List<Integer> result = new ArrayList<>();
    int i = 0, j = 0, k = 0;
    
    while (i < arr1.length && j < arr2.length && k < arr3.length) {
        if (arr1[i] == arr2[j] && arr2[j] == arr3[k]) {
            result.add(arr1[i]);
            i++; j++; k++;
        }
        else if (arr1[i] < arr2[j]) i++;
        else if (arr2[j] < arr3[k]) j++;
        else k++;
    }
    return result;
}
