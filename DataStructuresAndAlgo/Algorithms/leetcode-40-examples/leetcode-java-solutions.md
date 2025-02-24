# Top 10 LeetCode Problems - Java Solutions with Visualizations

## 1. Two Sum
**Difficulty**: Easy  
**Time**: O(n) | **Space**: O(n)

```java
public int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
        int complement = target - nums[i];
        if (map.containsKey(complement)) {
            return new int[] { map.get(complement), i };
        }
        map.put(nums[i], i);
    }
    return new int[] {};
}
```

```
Visualization:
Array: [2,7,11,15], Target: 9

Step 1: num = 2
map = {2: 0}
complement = 7 (not found)

Step 2: num = 7
map = {2: 0, 7: 1}
complement = 2 (found!)
Return [0, 1]
```

## 2. Add Two Numbers
**Difficulty**: Medium  
**Time**: O(max(m,n)) | **Space**: O(max(m,n))

```java
public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode(0);
    ListNode curr = dummy;
    int carry = 0;
    
    while (l1 != null || l2 != null || carry != 0) {
        int x = (l1 != null) ? l1.val : 0;
        int y = (l2 != null) ? l2.val : 0;
        
        int sum = x + y + carry;
        carry = sum / 10;
        
        curr.next = new ListNode(sum % 10);
        curr = curr.next;
        
        if (l1 != null) l1 = l1.next;
        if (l2 != null) l2 = l2.next;
    }
    
    return dummy.next;
}
```

```
Visualization:
Input: 
L1: 2->4->3
L2: 5->6->4

Process:
2+5 = 7  (carry: 0)  Result: 7->
4+6 = 10 (carry: 1)  Result: 7->0->
3+4+1 = 8            Result: 7->0->8
```

## 3. Longest Substring Without Repeating Characters
**Difficulty**: Medium  
**Time**: O(n) | **Space**: O(min(m,n))

```java
public int lengthOfLongestSubstring(String s) {
    Map<Character, Integer> map = new HashMap<>();
    int maxLength = 0;
    int left = 0;
    
    for (int right = 0; right < s.length(); right++) {
        char c = s.charAt(right);
        if (map.containsKey(c) && map.get(c) >= left) {
            left = map.get(c) + 1;
        }
        maxLength = Math.max(maxLength, right - left + 1);
        map.put(c, right);
    }
    
    return maxLength;
}
```

```
Visualization:
String: "abcabcbb"

Window movements:
[a]     -> length = 1
[ab]    -> length = 2
[abc]   -> length = 3
[bca]   -> length = 3
[cab]   -> length = 3
[bc]    -> length = 2
[cb]    -> length = 2
[b]     -> length = 1

Max Length = 3
```

## 4. Median of Two Sorted Arrays
**Difficulty**: Hard  
**Time**: O(log(min(m,n))) | **Space**: O(1)

```java
public double findMedianSortedArrays(int[] nums1, int[] nums2) {
    if (nums1.length > nums2.length) {
        return findMedianSortedArrays(nums2, nums1);
    }
    
    int x = nums1.length;
    int y = nums2.length;
    int low = 0;
    int high = x;
    
    while (low <= high) {
        int partitionX = (low + high) / 2;
        int partitionY = (x + y + 1) / 2 - partitionX;
        
        int maxLeftX = (partitionX == 0) ? Integer.MIN_VALUE : nums1[partitionX - 1];
        int minRightX = (partitionX == x) ? Integer.MAX_VALUE : nums1[partitionX];
        
        int maxLeftY = (partitionY == 0) ? Integer.MIN_VALUE : nums2[partitionY - 1];
        int minRightY = (partitionY == y) ? Integer.MAX_VALUE : nums2[partitionY];
        
        if (maxLeftX <= minRightY && maxLeftY <= minRightX) {
            if ((x + y) % 2 == 0) {
                return (Math.max(maxLeftX, maxLeftY) + Math.min(minRightX, minRightY)) / 2.0;
            } else {
                return Math.max(maxLeftX, maxLeftY);
            }
        } else if (maxLeftX > minRightY) {
            high = partitionX - 1;
        } else {
            low = partitionX + 1;
        }
    }
    throw new IllegalArgumentException();
}
```

```
Visualization:
nums1: [1,3,8,9,15]
nums2: [7,11,18,19,21,25]

Partition process:
Step 1:
Left1: [1,3] | Right1: [8,9,15]
Left2: [7,11] | Right2: [18,19,21,25]

Step 2:
Left1: [1] | Right1: [3,8,9,15]
Left2: [7,11,18] | Right2: [19,21,25]

Result: Median = (11 + 18) / 2 = 14.5
```

## 5. Longest Palindromic Substring
**Difficulty**: Medium  
**Time**: O(n²) | **Space**: O(1)

```java
public String longestPalindrome(String s) {
    if (s == null || s.length() < 2) return s;
    
    int start = 0, end = 0;
    
    for (int i = 0; i < s.length(); i++) {
        int len1 = expandAroundCenter(s, i, i);
        int len2 = expandAroundCenter(s, i, i + 1);
        int len = Math.max(len1, len2);
        
        if (len > end - start) {
            start = i - (len - 1) / 2;
            end = i + len / 2;
        }
    }
    
    return s.substring(start, end + 1);
}

private int expandAroundCenter(String s, int left, int right) {
    while (left >= 0 && right < s.length() 
           && s.charAt(left) == s.charAt(right)) {
        left--;
        right++;
    }
    return right - left - 1;
}
```

```
Visualization:
String: "babad"

Expansion process:
Center 'b': b
Center 'a': bab ✓
Center 'b': aba ✓
Center 'a': a
Center 'd': d

Returns: "bab" (or "aba")
```

## 6. ZigZag Conversion
**Difficulty**: Medium  
**Time**: O(n) | **Space**: O(n)

```java
public String convert(String s, int numRows) {
    if (numRows == 1) return s;
    
    StringBuilder[] rows = new StringBuilder[numRows];
    for (int i = 0; i < numRows; i++) {
        rows[i] = new StringBuilder();
    }
    
    int currentRow = 0;
    boolean goingDown = false;
    
    for (char c : s.toCharArray()) {
        rows[currentRow].append(c);
        
        if (currentRow == 0 || currentRow == numRows - 1) {
            goingDown = !goingDown;
        }
        
        currentRow += goingDown ? 1 : -1;
    }
    
    StringBuilder result = new StringBuilder();
    for (StringBuilder row : rows) {
        result.append(row);
    }
    
    return result.toString();
}
```

```
Visualization:
Input: s = "PAYPALISHIRING", numRows = 3

P   A   H   N
A P L S I I G
Y   I   R

Process:
Row 0: P   A   H   N
Row 1: A P L S I I G
Row 2: Y   I   R

Output: "PAHNAPLSIIGYIR"
```

## 7. Reverse Integer
**Difficulty**: Easy  
**Time**: O(log(x)) | **Space**: O(1)

```java
public int reverse(int x) {
    int result = 0;
    
    while (x != 0) {
        int pop = x % 10;
        x /= 10;
        
        if (result > Integer.MAX_VALUE/10 || 
            (result == Integer.MAX_VALUE/10 && pop > 7)) return 0;
        if (result < Integer.MIN_VALUE/10 || 
            (result == Integer.MIN_VALUE/10 && pop < -8)) return 0;
            
        result = result * 10 + pop;
    }
    
    return result;
}
```

```
Visualization:
Input: x = 123

Step 1: 123 % 10 = 3, result = 3
Step 2: 12 % 10 = 2, result = 32
Step 3: 1 % 10 = 1, result = 321

Output: 321
```

## 8. String to Integer (atoi)
**Difficulty**: Medium  
**Time**: O(n) | **Space**: O(1)

```java
public int myAtoi(String s) {
    if (s == null || s.length() == 0) return 0;
    
    int index = 0;
    int sign = 1;
    int result = 0;
    
    // Skip leading whitespace
    while (index < s.length() && s.charAt(index) == ' ') {
        index++;
    }
    
    // Check sign
    if (index < s.length() && (s.charAt(index) == '+' || s.charAt(index) == '-')) {
        sign = s.charAt(index) == '+' ? 1 : -1;
        index++;
    }
    
    // Process digits
    while (index < s.length() && Character.isDigit(s.charAt(index))) {
        int digit = s.charAt(index) - '0';
        
        // Check overflow
        if (result > Integer.MAX_VALUE/10 || 
            (result == Integer.MAX_VALUE/10 && digit > Integer.MAX_VALUE % 10)) {
            return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        }
        
        result = result * 10 + digit;
        index++;
    }
    
    return sign * result;
}
```

```
Visualization:
Input: "   -42"

Step 1: Skip spaces "   -42" -> "-42"
Step 2: Process sign "-42" -> "42", sign = -1
Step 3: Process digits:
        4: result = 4
        2: result = 42
Step 4: Apply sign: -42

Output: -42
```

## 9. Palindrome Number
**Difficulty**: Easy  
**Time**: O(log(x)) | **Space**: O(1)

```java
public boolean isPalindrome(int x) {
    if (x < 0 || (x % 10 == 0 && x != 0)) return false;
    
    int revertedNumber = 0;
    while (x > revertedNumber) {
        revertedNumber = revertedNumber * 10 + x % 10;
        x /= 10;
    }
    
    return x == revertedNumber || x == revertedNumber/10;
}
```

```
Visualization:
Input: x = 121

Step 1: x = 121, revertedNumber = 1
Step 2: x = 12, revertedNumber = 12
Step 3: x = 1, revertedNumber = 12

Check: 1 == 12/10 (true)
Output: true
```

## 10. Regular Expression Matching
**Difficulty**: Hard  
**Time**: O(mn) | **Space**: O(mn)

```java
public boolean isMatch(String s, String p) {
    boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];
    dp[0][0] = true;
    
    // Handle patterns like a*, a*b*, etc.
    for (int j = 2; j <= p.length(); j++) {
        if (p.charAt(j-1) == '*') {
            dp[0][j] = dp[0][j-2];
        }
    }
    
    for (int i = 1; i <= s.length(); i++) {
        for (int j = 1; j <= p.length(); j++) {
            char sChar = s.charAt(i-1);
            char pChar = p.charAt(j-1);
            
            if (pChar == '.' || pChar == sChar) {
                dp[i][j] = dp[i-1][j-1];
            } else if (pChar == '*') {
                dp[i][j] = dp[i][j-2]; // Zero occurrence
                char prevChar = p.charAt(j-2);
                if (prevChar == '.' || prevChar == sChar) {
                    dp[i][j] = dp[i][j] || dp[i-1][j];
                }
            }
        }
    }
    
    return dp[s.length()][p.length()];
}
```

```
Visualization:
Input: s = "aa", p = "a*"

DP Table:
    ø  a  *
ø   T  F  T
a   F  T  T
a   F  F  T

Process:
1. Empty pattern matches empty string
2. a* can match zero or more 'a's
3. For each character, check if it matches directly or via '*'

Output: true
```

Each solution includes:
- Complete Java implementation
- Time and space complexity
- Visual representation of the algorithm
- Step-by-step process visualization
- Example input/output

Would you like me to explain any of these solutions in more detail?
