package com.svmc.footballMatching.ui.personalProfile;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.Timestamp;
import com.svmc.footballMatching.R;
import com.svmc.footballMatching.data.enumeration.Result;
import com.svmc.footballMatching.data.model.user.User;
import com.svmc.footballMatching.data.session.Session;
import com.svmc.footballMatching.databinding.CustomLoadingLayoutBinding;
import com.svmc.footballMatching.databinding.FragmentEditPersonalBasicInformationBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class EditPersonalBasicInformationFragment extends Fragment {
    private final String TAG = "PersonalBasicFragment";
    private FragmentEditPersonalBasicInformationBinding binding;
    private EditPersonalBasicInformationViewModel viewModel;

    private Dialog loadingDialog;
    private CustomLoadingLayoutBinding customLoadingLayoutBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditPersonalBasicInformationBinding.inflate(inflater);
        binding.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this).get(EditPersonalBasicInformationViewModel.class);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view.getContext());
        observeLiveData(view.getContext());
    }

    private void initComponents(final Context context) {
        // Set up action bar
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_back_white_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().popBackStack();
                getParentFragmentManager().beginTransaction().detach(EditPersonalBasicInformationFragment.this).commit();
            }
        });

        binding.resetImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = Session.getInstance().getUserLiveData().getValue();
                binding.fullNameTextInputEditText.setText(user.getFullName());
                binding.birthdayTextInputEditText.setText(user.getBirthdayString());
                binding.addressTextInputEditText.setText(user.getAddress());
                binding.telephoneTextInputEditText.setText(user.getPhone());
            }
        });

        binding.confirmImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initLoadingDialog(context);
                loadingDialog.show();
                viewModel.updateProfile(getUpdateBasicInformation());
            }
        });

        binding.birthdayTextInputLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year, month, day;
                String birthdayString = binding.birthdayTextInputEditText.getText().toString();
                if (birthdayString.matches("^\\d{2}-\\d{2}-\\d{4}$")) {
                    String[] dates = birthdayString.split("-");
                    day = Integer.parseInt(dates[0]);
                    month = Integer.parseInt(dates[1]) - 1;
                    year = Integer.parseInt(dates[2]);
                } else {
                    Calendar calendar = Calendar.getInstance();
                    day = calendar.get(Calendar.DAY_OF_MONTH);
                    month = calendar.get(Calendar.MONTH);
                    year = calendar.get(Calendar.YEAR);
                }

                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        String dateString = "";
                        if (d < 10) dateString += "0";
                        dateString += d;
                        if (m < 10) dateString += "-0";
                        dateString += (m + 1);
                        dateString += "-" + y;
                        binding.birthdayTextInputEditText.setText(dateString);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    private void observeLiveData(final Context context) {
        viewModel.getResultLiveData().observe(getViewLifecycleOwner(), new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if (result == null) return;
                if (result == Result.SUCCESS) {
                    loadingDialog.dismiss();
                    Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
                    getParentFragmentManager().popBackStack();
                    getParentFragmentManager().beginTransaction().detach(EditPersonalBasicInformationFragment.this).commit();
                } else if (result == Result.FAILURE) {
                    loadingDialog.dismiss();
                    Toast.makeText(context, viewModel.getResultMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @SuppressWarnings("ConstantConditions")
    private Map<String, Object> getUpdateBasicInformation() {
        Map<String, Object> data = new HashMap<>();
        // full name
        data.put("fullName", binding.fullNameTextInputEditText.getText().toString());
        // birthday
        Timestamp birthday = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        try {
            Date birthdayDate = sdf.parse(binding.birthdayTextInputEditText.getText().toString());
            birthday = new Timestamp(birthdayDate);
        } catch (Exception e) {
            Log.d(TAG, "getUpdateBasicInformation: error getting birthday ", e);
        }
        data.put("birthday", birthday);
        // telephone
        data.put("phone", binding.telephoneTextInputEditText.getText().toString());
        // address
        data.put("address", binding.addressTextInputEditText.getText().toString());
        return data;
    }

    private void initLoadingDialog(Context context) {
        loadingDialog = new Dialog(context);
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customLoadingLayoutBinding = CustomLoadingLayoutBinding.inflate(getLayoutInflater());
        loadingDialog.setContentView(customLoadingLayoutBinding.getRoot());
        customLoadingLayoutBinding.title.setText(R.string.updating_information);
        loadingDialog.setCancelable(false);
    }
}
