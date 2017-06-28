package thanhhai.com.toeicpractice.ArrayPagerAdapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class ArrayPagerAdapter<T extends Fragment> extends PagerAdapter {
    public static final FragmentListener KEEP_FRAGMENT = new FragmentListener() {
        @Override
        public void attach(Fragment fragment, FragmentTransaction fragmentTransaction) {
            fragmentTransaction.attach(fragment);
        }

        @Override
        public void detach(Fragment fragment, FragmentTransaction fragmentTransaction) {
            fragmentTransaction.detach(fragment);
        }
    };

    private FragmentManager fm;
    private ArrayList<PageItem> entries = new ArrayList<>();
    private FragmentTransaction currentTransaction = null;
    private T currentFragment = null;

    private HashMap<Fragment, Integer> position = new HashMap<>();
    private FragmentListener listener = null;

    public ArrayPagerAdapter(FragmentManager fragmentManager, ArrayList<PageItem> items) {
        this(fragmentManager, items, null);
    }

    public ArrayPagerAdapter(FragmentManager fragmentManager, ArrayList<PageItem> items,
                             FragmentListener fragmentListener) {
        this.fm = fragmentManager;
        this.entries = new ArrayList<>();
        for (PageItem item : items) {
            checkDuplicate(item.getPageInfo());
            entries.add(item);
        }

        this.listener = fragmentListener;
        if (listener == null) {
            this.listener = KEEP_FRAGMENT;
        }
    }

    private void checkDuplicate(PageInfo item) {
        for (PageItem entry : entries) {
            if (entry.getPageInfo().getTag().equals(item.getTag())) {
                throw new RuntimeException("Duplicate tag");
            }
        }
    }

    public abstract T newInstance(PageInfo pageInfo);

    public T getCurrentFragment() {
        return currentFragment;
    }

    @Override
    public int getCount() {
        return entries.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (currentTransaction == null) {
            currentTransaction = fm.beginTransaction();
        }
        Fragment fragment = getFragmentInPosition(position);
        if (fragment == null) {
            fragment = newInstance(entries.get(position).getPageInfo());
            currentTransaction.add(container.getId(), fragment, getTag(position));
        } else {
            if (fragment.getId() == container.getId()) {
                listener.attach(fragment, currentTransaction);
            } else {
                fm.beginTransaction().remove(fragment).commit();
                fm.executePendingTransactions();
                currentTransaction.add(container.getId(), fragment, getTag(position));
            }
        }
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (currentTransaction == null) {
            currentTransaction = fm.beginTransaction();
        }
        listener.detach((Fragment) object, currentTransaction);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        T fragment = (T) object;
        if (fragment != currentFragment) {
            if (currentFragment != null) {
                currentFragment.setMenuVisibility(false);
                currentFragment.setUserVisibleHint(false);
            }

            if (fragment != null) {
                fragment.setMenuVisibility(true);
                fragment.setUserVisibleHint(true);
            }

            currentFragment = fragment;
        }
    }


    private String getTag(int position) {
        return entries.get(position).getPageInfo().getTag();
    }


    @Override
    public void finishUpdate(ViewGroup container) {
        if (currentTransaction != null) {
            currentTransaction.commitAllowingStateLoss();
            currentTransaction = null; //leak
            fm.executePendingTransactions();
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        Fragment fragment = (Fragment) object;
        if (fragment != null) {
            return fragment.getView().equals(view);
        } else {
            return false;
        }
    }

    @Override
    public Parcelable saveState() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(KEY, entries);
        return bundle;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        if (state == null) {

        } else {
            Bundle bundle = (Bundle) state;
            bundle.setClassLoader(getClass().getClassLoader());
            entries = bundle.getParcelableArrayList(KEY);
            notifyDataSetChanged();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return getPageInfo(position).getTitle();
    }

    public PageInfo getPageInfo(int position) {
        return entries.get(position).getPageInfo();
    }

    @Override
    public int getItemPosition(Object object) {
        Fragment fragment = (Fragment) object;
        Integer index = position.get(fragment);
        if (index == null) {
            return PagerAdapter.POSITION_UNCHANGED;
        }
        return index;
    }


    ////////////////////////
    @SuppressWarnings("unchecked")
    public T getFragmentInPosition(int position) {
        return (T) fm.findFragmentByTag(getTag(position));
    }

    public T getFragmentByTag(String tag) {
        return (T) fm.findFragmentByTag(tag);
    }

    public int getPositionForTag(String tag) {
        for (int i = 0; i < entries.size(); i++) {
            PageItem entry = entries.get(i);
            if (entry.getPageInfo().getTag().equals(tag)) {
                return i;
            }
        }
        return -1;
    }

    public void add(PageInfo pageInfo) {
        checkDuplicate(pageInfo);
        position.clear();
        entries.add(new PageItem(pageInfo));
        notifyDataSetChanged();
    }

    public void insert(PageInfo pageInfo, int insertPosition) {
        checkDuplicate(pageInfo);
        position.clear();
        for (int i = insertPosition; i < entries.size(); i++) {
            Fragment fragment = getFragmentInPosition(i);
            if (fragment != null) {
                position.put(fragment, i + 1);
            }
        }
        entries.add(insertPosition, new PageItem(pageInfo));
        notifyDataSetChanged();
    }

    public void delete(int position) {
        this.position.clear();
        Fragment fragment = getFragmentInPosition(position);
        if (fragment != null) {
            this.position.put(fragment, POSITION_NONE);
        }

        for (int i = position + 1; i < entries.size(); i++) {
            fragment = getFragmentInPosition(i);
            if (fragment != null) {
                this.position.put(fragment, i - 1);
            }
        }
        entries.remove(position);
        notifyDataSetChanged();
    }

    //////////////////////////////////
    public static final String KEY = "KEY_ENTRIES";
}
