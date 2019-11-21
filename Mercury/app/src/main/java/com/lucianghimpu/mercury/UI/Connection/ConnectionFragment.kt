package com.lucianghimpu.mercury.UI.Connection

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.lucianghimpu.mercury.R
import kotlinx.android.synthetic.main.connection_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConnectionFragment : Fragment() {

    private val viewModel by viewModel<ConnectionViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.connection_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        connectedServerLabel.text = viewModel.TAG

    }
}
