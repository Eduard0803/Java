class sort{
    private static void merge(int[] v, int l, int m, int r){
        int[] s = new int[r-l+1];
        int ia=l, ib=m+1, iaux=0;

        while(ia <= m && ib <= r){
            if(v[ia] < v[ib])
                s[iaux++] = v[ia++];
            else
                s[iaux++] = v[ib++];
        }
        while(ia <= m)
            s[iaux++] = v[ia++];
        while(ib <= r)
            s[iaux++] = v[ib++];
        iaux=0;
        for(int i=l; i<=r; i++)
            v[i] = s[iaux++];
    }
    public static void merge_sort(int[] v, int l, int r){
        if(r <= l)
            return;
        int m = l+(r-l)/2;
        merge_sort(v, l, m);
        merge_sort(v, m+1, r);
        merge(v, l, m, r);
    }
}