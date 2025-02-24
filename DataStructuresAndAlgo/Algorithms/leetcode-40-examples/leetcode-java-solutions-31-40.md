# LeetCode Problems 31-40 - Java Solutions with Visualizations

## 31. Next Permutation
**Difficulty**: Medium  
**Time**: O(n) | **Space**: O(1)

```java
public void nextPermutation(int[] nums) {
    int i = nums.length - 2;
    while (i >= 0 && nums[i] >= nums[i + 1]) {
        i--;
    }
    
    if (i >= 0) {
        int j = nums.length - 1;
        while (j >= 0 && nums[j] <= nums[i]) {
            j--;
        }
        swap(nums, i, j);
    }
    
    reverse(nums, i + 1);
}

private void swap(int[] nums, int i, int j) {
    int temp = nums[i];
    nums[i] = nums[j];
    nums[j] = temp;
}

private void reverse(int[] nums, int start) {
    int end = nums.length - 1;
    while (start < end) {
        swap(nums, start, end);
        start++;
        end--;
    }
}
```

```
Visualization:
Input: [1,2,3]

Step 1: Find decreasing element
[1,2,3] -> 2 is the pivot
           |

Step 2: Find next greater element
[1,2,3] -> 3 is next greater
           | |

Step 3: Swap and reverse
[1,3,2] -> Final result

Output: [1,3,2]
```

## 32. Longest Valid Parentheses
**Difficulty**: Hard  
**Time**: O(n) | **Space**: O(n)

```java
public int longestValidParentheses(String s) {
    Stack<Integer> stack = new Stack<>();
    stack.push(-1);
    int maxLen = 0;
    
    for (int i = 0; i < s.length(); i++) {
        if (s.charAt(i) == '(') {
            stack.push(i);
        } else {
            stack.pop();
            if (stack.empty()) {
                stack.push(i);
            } else {
                maxLen = Math.max(maxLen, i - stack.peek());
            }
        }
    }
    
    return maxLen;
}
```

```
Visualization:
Input: "()(()"

Stack operations:
-1 -> push (initial)
0 -> push '('
pop and calculate -> length = 2
2 -> push '('
3 -> push '('
4 -> pop and calculate -> length = 2

Output: 2
```

## 33. Search in Rotated Sorted Array
**Difficulty**: Medium  
**Time**: O(log n) | **Space**: O(1)

```java
public int search(int[] nums, int target) {
    int left = 0, right = nums.length - 1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        
        if (nums[mid] == target) {
            return mid;
        }
        
        if (nums[left] <= nums[mid]) {
            if (target >= nums[left] && target < nums[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        } else {
            if (target > nums[mid] && target <= nums[right]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
    }
    
    return -1;
}
```

```
Visualization:
Input: nums = [4,5,6,7,0,1,2], target = 0

Step 1: [4,5,6,7,0,1,2]
         L   M     R
         4 < 7 (sorted left half)
         target = 0 not in [4,7]
         
Step 2: [4,5,6,7,0,1,2]
                 L M R
                 0 < 1 (sorted left half)
                 target = 0 found!

Output: 4
```

## 34. Find First and Last Position of Element in Sorted Array
**Difficulty**: Medium  
**Time**: O(log n) | **Space**: O(1)

```java
public int[] searchRange(int[] nums, int target) {
    int[] result = {-1, -1};
    
    result[0] = findPosition(nums, target, true);
    result[1] = findPosition(nums, target, false);
    
    return result;
}

private int findPosition(int[] nums, int target, boolean isFirst) {
    int left = 0, right = nums.length - 1;
    int result = -1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        
        if (nums[mid] == target) {
            result = mid;
            if (isFirst) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        } else if (nums[mid] < target) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    
    return result;
}
```

```
Visualization:
Input: nums = [5,7,7,8,8,10], target = 8

First position search:
Step 1: [5,7,7,8,8,10]
         L   M     R
Step 2: [5,7,7,8,8,10]
               L M R
Result: 3

Last position search:
Step 1: [5,7,7,8,8,10]
         L   M     R
Step 2: [5,7,7,8,8,10]
               L M R
Result: 4

Output: [3,4]
```

## 35. Search Insert Position
**Difficulty**: Easy  
**Time**: O(log n) | **Space**: O(1)

```java
public int searchInsert(int[] nums, int target) {
    int left = 0;
    int right = nums.length - 1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        
        if (nums[mid] == target) {
            return mid;
        } else if (nums[mid] < target) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    
    return left;
}
```

```
Visualization:
Input: nums = [1,3,5,6], target = 5

Step 1: [1,3,5,6]
         L M   R
         3 < 5, move left

Step 2: [1,3,5,6]
             M R
         5 = 5, found!

Output: 2
```

## 36. Valid Sudoku
**Difficulty**: Medium  
**Time**: O(1) | **Space**: O(1)

```java
public boolean isValidSudoku(char[][] board) {
    Set<String> seen = new HashSet<>();
    
    for (int i = 0; i < 9; i++) {
        for (int j = 0; j < 9; j++) {
            char number = board[i][j];
            if (number != '.') {
                if (!seen.add(number + " in row " + i) ||
                    !seen.add(number + " in column " + j) ||
                    !seen.add(number + " in block " + i/3 + "-" + j/3))
                    return false;
            }
        }
    }
    
    return true;
}
```

```
Visualization:
Input: 
5 3 . | . 7 . | . . .
6 . . | 1 9 5 | . . .
. 9 8 | . . . | . 6 .
------+-------+------
8 . . | . 6 . | . . 3
4 . . | 8 . 3 | . . 1
7 . . | . 2 . | . . 6
------+-------+------
. 6 . | . . . | 2 8 .
. . . | 4 1 9 | . . 5
. . . | . 8 . | . 7 9

Check for each number:
- Row constraint
- Column constraint
- 3x3 box constraint

Output: true
```

## 37. Sudoku Solver
**Difficulty**: Hard  
**Time**: O(9^(n*n)) | **Space**: O(n*n)

```java
public void solveSudoku(char[][] board) {
    solve(board);
}

private boolean solve(char[][] board) {
    for (int i = 0; i < 9; i++) {
        for (int j = 0; j < 9; j++) {
            if (board[i][j] == '.') {
                for (char num = '1'; num <= '9'; num++) {
                    if (isValid(board, i, j, num)) {
                        board[i][j] = num;
                        if (solve(board)) {
                            return true;
                        }
                        board[i][j] = '.';
                    }
                }
                return false;
            }
        }
    }
    return true;
}

private boolean isValid(char[][] board, int row, int col, char num) {
    for (int x = 0; x < 9; x++) {
        if (board[row][x] == num) return false;
        if (board[x][col] == num) return false;
        if (board[3 * (row/3) + x/3][3 * (col/3) + x%3] == num) return false;
    }
    return true;
}
```

```
Visualization:
Input:
5 3 . | . 7 . | . . .
6 . . | 1 9 5 | . . .
. 9 8 | . . . | . 6 .

Process:
1. Find empty cell
2. Try numbers 1-9
3. Check if valid
4. Backtrack if needed

Final Output:
5 3 4 | 6 7 8 | 9 1 2
6 7 2 | 1 9 5 | 3 4 8
1 9 8 | 3 4 2 | 5 6 7
```

## 38. Count and Say
**Difficulty**: Medium  
**Time**: O(n * 2^n) | **Space**: O(2^n)

```java
public String countAndSay(int n) {
    if (n == 1) return "1";
    
    String prev = countAndSay(n - 1);
    StringBuilder result = new StringBuilder();
    
    int count = 1;
    char current = prev.charAt(0);
    
    for (int i = 1; i < prev.length(); i++) {
        if (prev.charAt(i) == current) {
            count++;
        } else {
            result.append(count).append(current);
            current = prev.charAt(i);
            count = 1;
        }
    }
    
    result.append(count).append(current);
    return result.toString();
}
```

```
Visualization:
Process for n = 4:

n = 1: "1"
n = 2: "11" (one 1)
n = 3: "21" (two 1s)
n = 4: "1211" (one 2, one 1)

Output: "1211"
```

## 39. Combination Sum
**Difficulty**: Medium  
**Time**: O(N^(T/M)) | **Space**: O(T/M)

```java
public List<List<Integer>> combinationSum(int[] candidates, int target) {
    List<List<Integer>> result = new ArrayList<>();
    backtrack(result, new ArrayList<>(), candidates, target, 0);
    return result;
}

private void backtrack(List<List<Integer>> result, List<Integer> current,
                      int[] candidates, int remaining, int start) {
    if (remaining < 0) return;
    if (remaining == 0) {
        result.add(new ArrayList<>(current));
        return;
    }
    
    for (int i = start; i < candidates.length; i++) {
        current.add(candidates[i]);
        backtrack(result, current, candidates, remaining - candidates[i], i);
        current.remove(current.size() - 1);
    }
}
```

```
Visualization:
Input: candidates = [2,3,6,7], target = 7

Backtracking tree:
                    []
         2/         3|          6\         7\
       [2]         [3]         [6]        [7]✓
    2/   3\      3|   6\      7|
  [2,2] [2,3]  [3,3] [3,6]  [6,7]
   ...    ...    ...

Output: [[2,2,3], [7]]
```

## 40. Combination Sum II
**Difficulty**: Medium  
**Time**: O(2^n) | **Space**: O(n)

```java
public List<List<Integer>> combinationSum2(int[] candidates, int target) {
    List<List<Integer>> result = new ArrayList<>();
    Arrays.sort(candidates);
    backtrack(result, new ArrayList<>(), candidates, target, 0);
    return result;
}

private void backtrack(List<List<Integer>> result, List<Integer> current,
                      int[] candidates, int remaining, int start) {
    if (remaining < 0) return;
    if (remaining == 0) {
        result.add(new ArrayList<>(current));
        return;
    }
    
    for (int i = start; i < candidates.length; i++) {
        if (i > start && candidates[i] == candidates[i-1]) continue;
        current.add(candidates[i]);
        backtrack(result, current, candidates, remaining - candidates[i], i + 1);
        current.remove(current.size() - 1);
    }
}
```

```
Visualization:
Input: candidates = [10,1,2,7,6,1,5], target = 8

First sort: [1,1,2,5,6,7,10]

Backtracking tree:
                []
        1/      1\      2\     5\    6\
      [1]      [1]     [2]    [5]   [6]
    1/  2\    2/  5\    5\     2\    2\
[1,1] [1,2] [1,5] ...  ...    ...   ...

Output: [[1,1,6], [1,2,5], [1,7], [2,6]]
```

Each solution includes:
- Complete Java implementation
- Time and space complexity
- Visual representation of the algorithm
- Step-by-step process visualization
- Example input/output
