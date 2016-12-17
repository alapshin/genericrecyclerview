package com.alapshin.genericrecyclerview;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * This class is the element that ties {@link RecyclerView.Adapter} together with {@link
 * ViewHolderDelegate}.
 * <p>
 * So you have to add / register your {@link ViewHolderDelegate}s to this manager by calling {@link
 * #addDelegate(ViewHolderDelegate)}
 * </p>
 *
 * <p>
 * Next you have to add this ViewHolderDelegateManager to the {@link RecyclerView.Adapter} by
 * calling corresponding methods:
 * </p>
 * <ul>
 * <li> {@link ViewHolderDelegateManager#getItemViewType(Item)}:
 * Must be called from {@link RecyclerView.Adapter#getItemViewType(int)}</li>
 * <li> {@link ViewHolderDelegateManager#onCreateViewHolder(ViewGroup, int)}:
 * Must be called from {@link RecyclerView.Adapter#onCreateViewHolder(ViewGroup, int)}</li>
 * <li> {@link ViewHolderDelegateManager#onBindViewHolder(RecyclerView.ViewHolder, Item)}:
 * Must be called from {@link RecyclerView.Adapter#onBindViewHolder(RecyclerView.ViewHolder, int)}</li>
 * </ul>
 * <p>
 * You can also set a fallback {@link ViewHolderDelegate} by using {@link
 * ViewHolderDelegateManager#setFallbackDelegate(ViewHolderDelegate)} that will be used if no
 * {@link ViewHolderDelegate} is responsible to handle a certain view type. If no fallback is specified, an Exception
 * will be thrown if no {@link ViewHolderDelegate} is responsible to handle a certain view type
 * </p>
 *
 * @param <T> item type
 *
 * @author Hannes Dorfmann
 * @author Andrei Lapshin
 */
public class ViewHolderDelegateManager<T extends Item, VH extends RecyclerView.ViewHolder> {
    static final int FALLBACK_DELEGATE_VIEW_TYPE = Integer.MAX_VALUE - 1;

    private ViewHolderDelegate<T, ? extends VH> fallbackDelegate;
    /**
     * Map for ViewType to ViewHolderDelegate
     */
    SparseArrayCompat<ViewHolderDelegate<T, ? extends VH>> delegates = new SparseArrayCompat<>();

    /**
     * Adds an {@link ViewHolderDelegate}.
     * <b>This method automatically assign internally the view type integer by using the next
     * unused</b>
     *
     * Internally calls {@link #addDelegate(ViewHolderDelegate, int, boolean)} with
     * allowReplacingDelegate = false as parameter.
     *
     * @param delegate the delegate to add
     * @return self
     * @throws NullPointerException if passed delegate is null
     * @see #addDelegate(ViewHolderDelegate, int)
     * @see #addDelegate(ViewHolderDelegate, int, boolean)
     */
    public ViewHolderDelegateManager<T, VH> addDelegate(@NonNull ViewHolderDelegate<T, ? extends VH> delegate) {
        // algorithm could be improved since there could be holes,
        // but it's very unlikely that we reach Integer.MAX_VALUE and run out of unused indexes
        int viewType = delegates.size();
        while (delegates.get(viewType) != null) {
            viewType++;
            if (viewType == FALLBACK_DELEGATE_VIEW_TYPE) {
                throw new IllegalArgumentException(
                        "Oops, we are very close to Integer.MAX_VALUE. It seems that there are no "
                        + "more free and unused view type integers left to add another ViewHolderDelegate.");
            }
        }
        return addDelegate(delegate, viewType, false);
    }

    /**
     * Adds an {@link ViewHolderDelegate} with the specified view type.
     *
     * Internally calls {@link #addDelegate(ViewHolderDelegate, int, boolean)} with
     * allowReplacingDelegate = false as parameter.
     *
     * @param viewType the view type integer if you want to assign manually the view type. Otherwise
     * use {@link #addDelegate(ViewHolderDelegate)} where a view type will be assigned automatically.
     * @param delegate the delegate to add
     * @return self
     * @throws NullPointerException if passed delegate is null
     * @see #addDelegate(ViewHolderDelegate)
     * @see #addDelegate(ViewHolderDelegate, int, boolean)
     */
    public ViewHolderDelegateManager<T, VH> addDelegate(@NonNull ViewHolderDelegate<T, ? extends VH> delegate,
                                                        int viewType) {
        return addDelegate(delegate, viewType, false);
    }

    public ViewHolderDelegateManager<T, VH> addDelegate(@NonNull ViewHolderDelegate<T, ? extends VH> delegate,
                                                    int viewType, boolean allowReplacingDelegate) {
        if (delegate == null) {
            throw new NullPointerException("ViewHolderDelegate is null!");
        }

        if (viewType == FALLBACK_DELEGATE_VIEW_TYPE) {
            throw new IllegalArgumentException("The view type = "
                    + FALLBACK_DELEGATE_VIEW_TYPE
                    + " is reserved for fallback adapter delegate (see setFallbackDelegate() )."
                    + " Please use another view type.");
        }

        if (!allowReplacingDelegate && delegates.get(viewType) != null) {
            throw new IllegalArgumentException(
                    "An ViewHolderDelegate is already registered for the viewType = "
                            + viewType
                            + ". Already registered ViewHolderDelegate is "
                            + delegates.get(viewType));
        }

        delegates.put(viewType, delegate);

        return this;
    }

    /**
     * Removes a previously registered delegate if and only if the passed delegate is registered
     * (checks the reference of the object). This will not remove any other delegate for the same
     * viewType (if there is any).
     *
     * @param delegate The delegate to remove
     * @return self
     */
    public ViewHolderDelegateManager<T, VH> removeDelegate(@NonNull ViewHolderDelegate<T, ? extends VH> delegate) {

        if (delegate == null) {
            throw new NullPointerException("ViewHolderDelegate is null");
        }

        int indexToRemove = delegates.indexOfValue(delegate);

        if (indexToRemove >= 0) {
            delegates.removeAt(indexToRemove);
        }
        return this;
    }

    /**
     * Removes the adapterDelegate for the given view types.
     *
     * @param viewType The Viewtype
     * @return self
     */
    public ViewHolderDelegateManager<T, VH> removeDelegate(int viewType) {
        delegates.remove(viewType);
        return this;
    }

    /**
     * Must be called from {@link RecyclerView.Adapter#getItemViewType(int)}. Internally it scans all
     * the registered {@link ViewHolderDelegate} and picks the right one to return the ViewType integer.
     *
     * @param item Item from adapter's data source
     * @return the ViewType (integer)
     * @throws IllegalArgumentException if no {@link ViewHolderDelegate} has been found that is
     * responsible for the given data element in data set (No {@link ViewHolderDelegate} for the given
     * ViewType)
     * @throws NullPointerException if items is null
     */
    public int getItemViewType(@NonNull T item) {

        int delegatesCount = delegates.size();
        for (int i = 0; i < delegatesCount; i++) {
            ViewHolderDelegate<T, ? extends VH> delegate = delegates.valueAt(i);
            if (delegate.isForViewType(item)) {
                return delegates.keyAt(i);
            }
        }

        if (fallbackDelegate != null) {
            return FALLBACK_DELEGATE_VIEW_TYPE;
        }

        throw new IllegalArgumentException(
                "No ViewHolderDelegate added that matches item in data source");
    }

    /**
     * This method must be called in {@link RecyclerView.Adapter#onCreateViewHolder(ViewGroup, int)}
     *
     * @param parent the parent
     * @param viewType the view type
     * @return The new created ViewHolder
     * @throws NullPointerException if no ViewHolderDelegate has been registered for ViewHolders
     * viewType
     */
    @NonNull
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolderDelegate<T, ? extends VH> delegate = delegates.get(viewType);
        if (delegate == null) {
            if (fallbackDelegate == null) {
                throw new NullPointerException("No ViewHolderDelegate added for ViewType " + viewType);
            } else {
                delegate = fallbackDelegate;
            }
        }

        VH vh = delegate.onCreateViewHolder(parent);
        if (vh == null) {
            throw new NullPointerException("ViewHolder returned from ViewHolderDelegate "
                    + delegate
                    + " for ViewType ="
                    + viewType
                    + " is null!");
        }
        return vh;
    }

    /**
     * Must be called from{@link RecyclerView.Adapter#onBindViewHolder(RecyclerView.ViewHolder, int)}
     *
     * @param item item
     * @param viewHolder the ViewHolder to bind
     * @throws NullPointerException if no ViewHolderDelegate has been registered for ViewHolders
     * viewType
     */
    public void onBindViewHolder(@NonNull VH viewHolder, @NonNull T item) {

        ViewHolderDelegate<T, ? extends VH> delegate = delegates.get(viewHolder.getItemViewType());
        if (delegate == null) {
            if (fallbackDelegate == null) {
                throw new NullPointerException(
                        "No ViewHolderDelegate added for ViewType " + viewHolder.getItemViewType());
            } else {
                delegate = fallbackDelegate;
            }
        }

        onBindViewHolderImpl(delegate, viewHolder, item);
    }

    /**
     * Set a fallback delegate that should be used if no {@link ViewHolderDelegate} has been found that
     * can handle a certain view type.
     *
     * @param fallbackDelegate The {@link ViewHolderDelegate} that should be used as fallback if no
     * other ViewHolderDelegate has handled a certain view type. <code>null</code> you can set this to
     * null if
     * you want to remove a previously set fallback ViewHolderDelegate
     * @return self
     */
    public ViewHolderDelegateManager<T, VH> setFallbackDelegate(
            @Nullable ViewHolderDelegate<T, ? extends VH> fallbackDelegate) {
        this.fallbackDelegate = fallbackDelegate;
        return this;
    }

    /**
     * Helper method to call {@link ViewHolderDelegate#onBindViewHolder(RecyclerView.ViewHolder, Item)}
     * with view holder casted to right type
     *
     * {@see http://www.angelikalanger.com/GenericsFAQ/FAQSections/ProgrammingIdioms.html#FAQ208}
     */
    private static <T extends Item, VH extends RecyclerView.ViewHolder> void onBindViewHolderImpl(
            ViewHolderDelegate<T, VH> reference, Object arg, T item) {
        reference.onBindViewHolder(reference.getViewHolderType().cast(arg), item);
    }
}
