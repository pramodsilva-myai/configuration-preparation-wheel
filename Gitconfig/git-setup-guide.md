# Git Setup and Configuration Guide

## 1. Install Git

### Windows
1. Download the installer from [git-scm.com](https://git-scm.com/download/win)
2. Run the installer and follow the prompts

### macOS
1. Install Homebrew if not already installed: `/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"`
2. Install Git: `brew install git`

### Linux (Ubuntu/Debian)
1. Open terminal and run: `sudo apt-get update`
2. Install Git: `sudo apt-get install git`

## 2. Configure Git

### Set your username and email
```bash
git config --global user.name "Your Name"
git config --global user.email "your.email@example.com"
```

### Set default branch name
```bash
git config --global init.defaultBranch main
```

### Set up SSH key (optional but recommended)
1. Generate SSH key: `ssh-keygen -t ed25519 -C "your.email@example.com"`
2. Add SSH key to ssh-agent: 
   ```bash
   eval "$(ssh-agent -s)"
   ssh-add ~/.ssh/id_ed25519
   ```
3. Add the public key to your Git account (GitHub, GitLab, etc.)

## 3. Initialize a Repository

1. Create a new directory: `mkdir my-project`
2. Navigate to the directory: `cd my-project`
3. Initialize Git: `git init`

## 4. Basic Git Workflow

1. Create or modify files in your project
2. Stage changes: `git add .`
3. Commit changes: `git commit -m "Your commit message"`
4. Push to remote (if set up): `git push origin main`

## 5. Useful Git Commands

- Check status: `git status`
- View commit history: `git log`
- Create a new branch: `git branch branch-name`
- Switch to a branch: `git checkout branch-name`
- Merge branches: `git merge branch-name`
- Pull changes from remote: `git pull origin main`

## 6. Git Best Practices

- Commit often with clear, concise messages
- Use branches for new features or bug fixes
- Review changes before committing
- Keep your local repository up to date
- Use `.gitignore` to exclude unnecessary files

Remember to adapt these instructions based on your specific needs and environment.
