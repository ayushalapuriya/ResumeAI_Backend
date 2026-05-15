# Professional Git Command Guide

Follow these commands to manage your microservices project professionally.

## 1. Daily Development (Working on a Service)

### Switch to your service branch
```bash
git checkout feature/auth-service
```

### Pull latest changes from dev (to stay updated)
```bash
git pull origin dev
```

### Commit your work
```bash
git add .
git commit -m "feat(service-name): brief description of changes"
```

### Push your service branch to GitHub
```bash
git push origin feature/auth-service
```

---

## 2. Merging into Dev (Integration)

Once your service is stable, merge it into the `dev` branch.

```bash
# 1. Switch to dev
git checkout dev

# 2. Pull latest dev
git pull origin dev

# 3. Merge your service branch
git merge feature/auth-service

# 4. Resolve conflicts if any, then push
git push origin dev
```

---

## 3. Production Release (Merge Dev into Main)

Only do this after integration testing on the `dev` branch is complete.

```bash
# 1. Switch to main
git checkout main

# 2. Pull latest main
git pull origin main

# 3. Merge dev into main
git merge dev

# 4. Tag the release (Optional but recommended)
git tag -a v1.0.0 -m "Initial microservices release"

# 5. Final push to main
git push origin main --tags
```

---

## 4. Conflict Resolution (The Safe Way)

If you have a conflict when merging:

1.  Git will tell you which files have conflicts.
2.  Open those files and look for `<<<<<<< HEAD` markers.
3.  Choose which code to keep and remove the markers.
4.  After fixing, run:
    ```bash
    git add <conflicted-file>
    git commit -m "chore: resolved merge conflict"
    ```

---

## 💡 Best Practices
- **Commit Small**: Commit often with small, logical changes.
- **Atomic Commits**: Each commit should do one thing.
- **Never push to main directly**: Always go through `dev`.
- **Review your diff**: Use `git diff` before committing to see what you changed.
