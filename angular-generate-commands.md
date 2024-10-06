# First, ensure Angular CLI is installed globally
npm install -g @angular/cli

# Generate Component
ng generate component path/component-name
ng g c path/component-name    # Shorthand

# Generate Interface
ng generate interface path/interface-name
ng g i path/interface-name    # Shorthand

# Generate Class
ng generate class path/class-name
ng g cl path/class-name       # Shorthand

# Generate Module
ng generate module path/module-name
ng g m path/module-name       # Shorthand

# Common options for generate commands
--dry-run          # Preview changes without writing to disk
--flat             # Don't create a folder
--skip-tests       # Don't create spec files
--standalone       # Create as standalone component

# Examples with paths
ng g c features/user/user-list
ng g i models/user
ng g cl models/user
ng g m features/user --routing  # Creates module with routing

# Generate component inside module
ng g c features/user/user-list --module=features/user

# Generate service
ng g service services/user

# Generate guard
ng g guard guards/auth

# Generate pipe
ng g pipe pipes/custom-pipe

# Generate a model
ng generate class models/YourModelName --type=model
