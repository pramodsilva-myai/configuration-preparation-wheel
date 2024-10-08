# TypeScript Best Practices Guide

## 1. Type System Usage
- Use explicit typing for function parameters and return types
- Leverage interface and type aliases for complex types
- Use union types and intersection types effectively
- Avoid using `any` unless absolutely necessary

```typescript
// Good
interface User {
  id: number;
  name: string;
  email: string;
}

function getUser(id: number): Promise<User> {
  // Implementation
}

// Avoid
function getUser(id): any {
  // Implementation
}
```

## 2. Null and Undefined Handling
- Use optional chaining (`?.`) and nullish coalescing (`??`)
- Enable strict null checks in tsconfig
- Use type guards to narrow types
- Be explicit about nullable properties

```typescript
interface Person {
  name: string;
  age?: number;  // Optional property
}

function greet(person: Person) {
  console.log(`Hello, ${person.name}!`);
  console.log(`Age: ${person.age ?? 'Not specified'}`);
}
```

## 3. Advanced Types
- Use generics for reusable code
- Leverage mapped types for type transformations
- Use utility types (Partial, Readonly, Pick, Record)
- Implement proper type guards

```typescript
function identity<T>(arg: T): T {
  return arg;
}

type ReadonlyUser = Readonly<User>;
type UserName = Pick<User, 'name'>;

function isUser(obj: any): obj is User {
  return 'id' in obj && 'name' in obj;
}
```

## 4. Code Organization
- Use modules for better code organization
- Implement barrel exports
- Follow consistent naming conventions
- Use namespaces sparingly (prefer modules)

```typescript
// users/types.ts
export interface User { /* ... */ }

// users/api.ts
export function getUser() { /* ... */ }

// users/index.ts (barrel)
export * from './types';
export * from './api';
```

## 5. Async Programming
- Use async/await over raw promises
- Properly type Promise returns
- Handle errors in async functions
- Use Promise.all for parallel operations

```typescript
async function fetchUserData(id: number): Promise<UserData> {
  try {
    const response = await fetch(`/api/users/${id}`);
    if (!response.ok) {
      throw new Error('User not found');
    }
    return response.json();
  } catch (error) {
    console.error('Error fetching user:', error);
    throw error;
  }
}
```

## 6. Configuration
- Use strict mode in tsconfig.json
- Enable all strict type checking options
- Configure proper module resolution
- Set appropriate target ECMAScript version

```json
{
  "compilerOptions": {
    "strict": true,
    "target": "ES2020",
    "module": "ESNext",
    "moduleResolution": "node",
    "esModuleInterop": true
  }
}
```

## 7. Error Handling
- Create custom error types
- Use discriminated unions for error handling
- Implement proper error boundaries
- Type error objects appropriately

```typescript
class ApiError extends Error {
  constructor(public statusCode: number, message: string) {
    super(message);
    this.name = 'ApiError';
  }
}

type Result<T> = 
  | { success: true; data: T }
  | { success: false; error: ApiError };
```

## 8. Testing
- Use Jest or similar testing framework
- Implement proper type coverage
- Use TypeScript-aware testing utilities
- Test type definitions

```typescript
describe('User functions', () => {
  it('should create user correctly', () => {
    const user: User = {
      id: 1,
      name: 'John Doe',
      email: 'john@example.com'
    };
    expect(createUser(user)).resolves.toEqual(user);
  });
});
```

## 9. Documentation
- Use TSDoc for documentation
- Document complex types
- Include examples in documentation
- Generate documentation using TypeDoc

```typescript
/**
 * Represents a user in the system
 * @interface User
 * @property {number} id - The unique identifier for the user
 * @property {string} name - The user's full name
 * @property {string} email - The user's email address
 */
interface User {
  id: number;
  name: string;
  email: string;
}
```

## 10. Performance
- Use const assertions for literal types
- Implement proper tree-shaking
- Be mindful of type inference performance
- Use appropriate data structures

## 11. Tooling
- Use ESLint with TypeScript parser
- Implement Prettier for consistent formatting
- Use TypeScript-aware IDE extensions
- Implement pre-commit hooks for type checking

## 12. Patterns and Anti-patterns
### Good Patterns
- Builder pattern with proper typing
- Factory functions with generics
- Dependency injection with interfaces

### Anti-patterns to Avoid
- Type assertions without checks
- Overuse of `any`
- Ignoring TypeScript errors
- Not using strict mode
